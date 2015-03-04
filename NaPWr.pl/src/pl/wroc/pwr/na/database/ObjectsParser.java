package pl.wroc.pwr.na.database;

import java.util.ArrayList;

import pl.wroc.pwr.na.database.objects.AddressObject;
import pl.wroc.pwr.na.database.objects.EventObject;
import pl.wroc.pwr.na.database.objects.OrganizationObject;
import pl.wroc.pwr.na.database.objects.ScheduleObject;
import pl.wroc.pwr.na.database.objects.TagObject;
import pl.wroc.pwr.na.database.parsers.AddressParser;
import pl.wroc.pwr.na.database.parsers.EventParser;
import pl.wroc.pwr.na.database.parsers.OrganizationParser;
import pl.wroc.pwr.na.database.parsers.ScheduleParser;
import pl.wroc.pwr.na.database.parsers.TagParser;

public class ObjectsParser {

	public static ArrayList<AddressObject> getAllAddressess() {
		AddressParser parser = new AddressParser();
		return parser.getAllAddressess();
	}

	public static ArrayList<EventObject> getAllEvents() {
		EventParser parser = new EventParser();
		return parser.getAllEvents();
	}

	public static ArrayList<TagObject> getAllTags() {
		TagParser parser = new TagParser();
		return parser.getAllTags();
	}

	public static ArrayList<OrganizationObject> getAllOrganizations() {
		OrganizationParser parser = new OrganizationParser();
		return parser.getAllOrganizations();
	}

	public static ArrayList<ScheduleObject> getAllScheduleObject(int userId) {
		ScheduleParser parser = new ScheduleParser();
		return parser.getAllScheduleObjects(userId);
	}
}
