package pl.wroc.pwr.na.events;

import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.database.DataBaseHandler;
import pl.wroc.pwr.na.database.objects.AddressObject;
import pl.wroc.pwr.na.database.objects.EventObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity {
	/***/
	public static final String EVENT_ID = "event_id";

	private EventObject event;
	private AddressObject address;

	private TextView title;
	private TextView place;
	private TextView date;
	private TextView dayOfWeek;
	private TextView hour;
	private TextView description;

	private ImageView likeStar;

	private DataBaseHandler db;

	private Activity partent = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		initializeDBObjects();
		initializeItems();
		prepareView();

		likeStar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!event.isLiked) {
					event.isLiked = true;
					Toast.makeText(partent, "Dodano do zapisanych.",
							Toast.LENGTH_SHORT).show();
					db.addEventToLiked(event.wydarzenieId);
					likeStar.setImageDrawable(getResources().getDrawable(
							R.drawable.like_star));
				} else {
					Toast.makeText(partent, "Usunięto z zapisanych.",
							Toast.LENGTH_SHORT).show();
					db.removeEventFromLiked(event.wydarzenieId);
					likeStar.setImageDrawable(getResources().getDrawable(
							R.drawable.empty_star));
				}

			}
		});
	}

	private void initializeDBObjects() {
		db = new DataBaseHandler(getApplicationContext());
		event = db.getEventObject(getId());
		address = db.getAddressObject(event.adresId);
	}

	private int getId() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		int id = -1;
		if (bundle != null) {
			id = bundle.getInt(EventActivity.EVENT_ID);
		}
		return id;
	}

	private void initializeItems() {
		title = (TextView) findViewById(R.id.text_event_title);
		place = (TextView) findViewById(R.id.event_place);
		date = (TextView) findViewById(R.id.text_event_date);
		dayOfWeek = (TextView) findViewById(R.id.dzien_tyg);
		description = (TextView) findViewById(R.id.event_content);
		hour = (TextView) findViewById(R.id.event_hour);
		likeStar = (ImageView) findViewById(R.id.event_like_star);
	}

	private void prepareView() {
		String placeString = address.adresMiasto + "";
		placeString += (event.wydarzenieSala.equals("null") ? "" : ", "
				+ event.wydarzenieSala);
		place.setText(placeString);

		title.setText(event.wydarzenieTytul);
		date.setText(event.getDay(event.wydarzenieDataPoczatek) + " "
				+ event.getMonth(event.wydarzenieDataPoczatek));
		description.setText(event.wydarzenieTresc);
		dayOfWeek.setText(event.getDayOfWeek(event.wydarzenieDataPoczatek)
				+ ",");

		String hourText = event.getHoour(event.wydarzenieDataPoczatek);
		if (hourText.equals("0:00")) {
			hour.setVisibility(View.GONE);
		} else {
			hour.setText(hourText);
		}

		if (event.isLiked) {
			likeStar.setImageDrawable(getResources().getDrawable(
					R.drawable.like_star));
		} else {
			likeStar.setImageDrawable(getResources().getDrawable(
					R.drawable.empty_star));
		}

	}
}
