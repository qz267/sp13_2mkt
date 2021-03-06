package com.secondmarket.batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.secondmarket.domain.Financial_Org;
import com.secondmarket.domain.Investor;
import com.secondmarket.domain.Location;

@Service("investorFilterService")
public class InvestorFilters {

	protected static Logger logger = Logger.getLogger("batch");
	
	public List<Investor> filterByStar(String starLevel, List<Investor> investors) {
		logger.debug("Retrieving all investors star filter");
		List<Investor> items = new ArrayList<Investor>();
		
		int level = Integer.parseInt(starLevel);
		double low = 0.0;
		double high = 0.0;
		switch(level){
		case 5 : 
			low = 0.8;
			high = 1.0;
			break;
		case 4 :
			low = 0.6;
			high = 1.0;
			break;
		case 3 :
			low = 0.4;
			high = 1.0;
			break;
		case 2 : 
			low = 0.2;
			high = 1.0;
			break;
		default :
			low = 0.0;
			high = 1.0;
			break;
		}
		
		for(Investor each : investors){
			if(each.getStar_score() <= high && each.getStar_score() > low){
				items.add(each);
			}
		}
		
		return items;
	}

	public List<Financial_Org> filterByStarFin(String starLevel, List<Financial_Org> finOrg) {
		logger.debug("Retrieving all institution investors star filter");
		List<Financial_Org> items = new ArrayList<Financial_Org>();
		
		int level = Integer.parseInt(starLevel);
		double low = 0.0;
		double high = 0.0;
		switch(level){
		case 5 : 
			low = 0.8;
			high = 1.0;
			break;
		case 4 :
			low = 0.6;
			high = 1.0;
			break;
		case 3 :
			low = 0.4;
			high = 1.0;
			break;
		case 2 : 
			low = 0.2;
			high = 1.0;
			break;
		default :
			low = 0.0;
			high = 1.0;
			break;
		}
		
		for(Financial_Org each : finOrg){
			if(each.getStar_score() <= high && each.getStar_score() > low){
				items.add(each);
			}
		}
		
		return items;
	}
	
	public List<Investor> filterIndividualInvstorsByLocation(String checkBoxVal, List<Investor> investors) 
	{
		logger.debug("Retrieving all investor location filter");
		String[] loc = checkBoxVal.split(",");
		List<Investor> items = new ArrayList<Investor>(); 

		for(Investor investor : investors)
		{
			List<Location> all_locations = investor.getLocations();
			done:for(Location each_location: all_locations)
			{
				String location_name = each_location.getName();
				if(location_name != null)
				{
					for (String each : loc) 
					{
						if (each.equals("1")){
							if (location_name.equalsIgnoreCase("san francisco")){
								items.add(investor);
								break done;
							}
						} 
						else if (each.equals("2")){
							if (location_name.equalsIgnoreCase("new york, ny")){
								items.add(investor);
								break done;
							}
						}
						else if (each.equals("3")){
							if (location_name.equalsIgnoreCase("los Angeles")){
								items.add(investor);
								break done;
							}
						}
						else if (each.equals("4")){
							if (location_name.equalsIgnoreCase("Toronto")){
								items.add(investor);
								break done;
							}
						}
						else if (each.equals("5")){
							if (location_name.equalsIgnoreCase("London")){
								items.add(investor);
								break done;
							}
						}
						else if (each.equals("6")){
							if (location_name.equalsIgnoreCase("Tokyo")){
								items.add(investor);
								break done;
							}
						}
						else if (each.equals("7")){
							if (!location_name.equalsIgnoreCase("san francisco") &&
									!location_name.equalsIgnoreCase("new york, ny") &&
									!location_name.equalsIgnoreCase("los Angeles") &&
									!location_name.equalsIgnoreCase("Toronto") &&
									!location_name.equalsIgnoreCase("London")&&
									!location_name.equalsIgnoreCase("Tokyo"))
								items.add(investor);
							break done;
						}
					}
				}
			}
		}
		return items; 
	}
	
	public List<Financial_Org> filterInstitutionalInvstorsByLocation(String checkBoxVal, List<Financial_Org> financialOrgs) 
	{
		logger.debug("Retrieving all investor location filter");
		String[] loc = checkBoxVal.split(",");
		List<Financial_Org> items = new ArrayList<Financial_Org>(); 

		for(Financial_Org financialOrg : financialOrgs)
		{
			List<Location> all_locations = financialOrg.getLocations();
			done:for(Location each_location: all_locations)
			{
				String location_name = each_location.getName();
				if(location_name != null)
				{
					for (String each : loc) 
					{
						if (each.equals("1")){
							if (location_name.equalsIgnoreCase("san francisco")){
								items.add(financialOrg);
								break done;
							}
						} 
						else if (each.equals("2")){
							if (location_name.equalsIgnoreCase("new york, ny")){
								items.add(financialOrg);
								break done;
							}
						}
						else if (each.equals("3")){
							if (location_name.equalsIgnoreCase("los Angeles")){
								items.add(financialOrg);
								break done;
							}
						}
						else if (each.equals("4")){
							if (location_name.equalsIgnoreCase("Toronto")){
								items.add(financialOrg);
								break done;
							}
						}
						else if (each.equals("5")){
							if (location_name.equalsIgnoreCase("London")){
								items.add(financialOrg);
								break done;
							}
						}
						else if (each.equals("6")){
							if (location_name.equalsIgnoreCase("Tokyo")){
								items.add(financialOrg);
								break done;
							}
						}
						else if (each.equals("7")){
							if (!location_name.equalsIgnoreCase("san francisco") &&
									!location_name.equalsIgnoreCase("new york, ny") &&
									!location_name.equalsIgnoreCase("los Angeles") &&
									!location_name.equalsIgnoreCase("Toronto") &&
									!location_name.equalsIgnoreCase("London")&&
									!location_name.equalsIgnoreCase("Tokyo"))
								items.add(financialOrg);
							break done;
						}
					}
				}
			}
		}
		return items; 
	}
}
