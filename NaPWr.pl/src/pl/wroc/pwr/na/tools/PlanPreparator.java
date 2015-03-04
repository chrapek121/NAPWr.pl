package pl.wroc.pwr.na.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pl.wroc.pwr.na.database.objects.ScheduleObject;
import android.util.Log;

@SuppressWarnings("unchecked")
public class PlanPreparator {

	public ArrayList<ScheduleObject> preparePlan(List<ScheduleObject> planList) {

		if (planList == null) {
			planList = new ArrayList<ScheduleObject>();
		}

		// inicjalizowanie
		Object[] dayOfWeek = new Object[7];
		dayOfWeek[0] = new ArrayList<ScheduleObject>();
		dayOfWeek[1] = new ArrayList<ScheduleObject>();
		dayOfWeek[2] = new ArrayList<ScheduleObject>();
		dayOfWeek[3] = new ArrayList<ScheduleObject>();
		dayOfWeek[4] = new ArrayList<ScheduleObject>();
		dayOfWeek[5] = new ArrayList<ScheduleObject>();
		dayOfWeek[6] = new ArrayList<ScheduleObject>();

		// dzielenie do tablic z dniami
		for (ScheduleObject po : planList) {
			switch (po.day) {
			case 0:
				((ArrayList<ScheduleObject>) dayOfWeek[1]).add(po);
				break;
			case 1:
				((ArrayList<ScheduleObject>) dayOfWeek[2]).add(po);
				break;
			case 2:
				((ArrayList<ScheduleObject>) dayOfWeek[3]).add(po);
				break;
			case 3:
				((ArrayList<ScheduleObject>) dayOfWeek[4]).add(po);
				break;
			case 4:
				((ArrayList<ScheduleObject>) dayOfWeek[5]).add(po);
				break;
			case 5:
				((ArrayList<ScheduleObject>) dayOfWeek[6]).add(po);
				break;
			case 6:
				((ArrayList<ScheduleObject>) dayOfWeek[0]).add(po);
				break;
			}
		}

		dayOfWeek[0] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[0]);
		dayOfWeek[1] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[1]);
		dayOfWeek[2] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[2]);
		dayOfWeek[3] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[3]);
		dayOfWeek[4] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[4]);
		dayOfWeek[5] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[5]);
		dayOfWeek[6] = sortPlan((ArrayList<ScheduleObject>) dayOfWeek[6]);

		// Tworzenia aktualnego planu
		ArrayList<ScheduleObject> plan = new ArrayList<ScheduleObject>();

		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int week = calendar.get(Calendar.WEEK_OF_YEAR) % 2;
		int dayIncremented = 1;

		// w week 0 - TP, 1 - TN
		// w planie 0 - T, 1 - TN, 2 - TP
		// w day 0 - niedziela, 1 - poniedzialek, ... , 6 - sobota

		day--;
		dayIncremented--;
		if (day < 0) {
			day = 6;
			week = Math.abs(week - 1);

		}
		for (int i = 0; i < 7; i++) {
			if (day > 6) {
				day = 0;
				week = (week + 1) % 2;
			}

			Log.d("plan", day + " " + dayIncremented);
			if (vaildDaysInPlanDay(
					((ArrayList<ScheduleObject>) dayOfWeek[day]), week) > 0) {
				plan.add(new ScheduleObject("separator", getSeparatorName(day,
						week, dayIncremented)));
				for (ScheduleObject po : ((ArrayList<ScheduleObject>) dayOfWeek[day])) {
					if (po.week == 0 || po.week == week || po.week % 2 == week) {
						plan.add(po);
					}
				}
			}
			day++;
			dayIncremented++;
		}

		return plan;
	}

	private ArrayList<ScheduleObject> sortPlan(ArrayList<ScheduleObject> toSort) {
		ArrayList<ScheduleObject> sorted = new ArrayList<ScheduleObject>();

		for (ScheduleObject po : toSort) {
			if (sorted.size() == 0) {
				sorted.add(po);
			} else {
				boolean added = false;
				for (int i = 0; i < sorted.size(); i++) {
					if (po.startHour < sorted.get(i).startHour && !added) {
						sorted.add(i, po);
						added = true;
					}
				}
				if (!added) {
					sorted.add(po);
				}
			}
		}

		return sorted;
	}

	private String getSeparatorName(int day, int week, int dayIncremented) {
		String separatorName = "";

		switch (day) {
		case 0:
			separatorName += "Niedziela, ";
			break;
		case 1:
			separatorName += "Poniedziałek, ";
			break;
		case 2:
			separatorName += "Wtorek, ";
			break;
		case 3:
			separatorName += "Środa, ";
			break;
		case 4:
			separatorName += "Czwartek, ";
			break;
		case 5:
			separatorName += "Piątek, ";
			break;
		case 6:
			separatorName += "Sobota, ";
			break;
		}

		switch (week) {
		case 0:
			separatorName += "TP, ";
			break;
		case 1:
			separatorName += "TN, ";
			break;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",
				Locale.ENGLISH);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, dayIncremented); // number of days to add
		separatorName += sdf.format(c.getTime()); // dt is now the new date

		return separatorName;
	}

	private int vaildDaysInPlanDay(ArrayList<ScheduleObject> planDay, int week) {
		int vaildDays = 0;
		for (ScheduleObject po : planDay) {
			if (po.week == 0 || po.week == week || po.week % 2 == week) {
				vaildDays++;
			}
		}
		return vaildDays;
	}
}
