package com.secondmarket.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import au.com.bytecode.opencsv.CSVReader;

import com.google.code.morphia.Datastore;
import com.secondmarket.common.CrunchbaseNamespace;
import com.secondmarket.common.MongoDBFactory;

public class DatabaseService 
{
	protected static Logger logger = Logger.getLogger("core"); 
	private static String companyFileName = "companySlug.csv";
	private static int angelListCallCounter = 0;
	
	public static void main(String args[])
	{
		PropertyConfigurator.configure("log4j.properties");
		
		logger.debug("Intializing Data store");
		Datastore ds = MongoDBFactory.getDataStore();
		
		List<String> companyPermalinks = getCompanyPermlinks();
		
		logger.debug("Collections persistence is Started");
		for(String permalink : companyPermalinks)
		{
			CompanyDatabaseService companyDatabaseService = new CompanyDatabaseService();
			Map<String, String> all_investors_Permalink = companyDatabaseService.populateSingleCompanyCollection(ds, permalink);
			
			CompanyDatabaseService companyDatabaseServiceAsInvestor = new CompanyDatabaseService();
			for(Entry<String, String> singleInvestor : all_investors_Permalink.entrySet())
			{
				if(singleInvestor.getValue().equals(CrunchbaseNamespace.PERSON.getLabel().toString()))
				{
					InvestorDatabaseService.populateInvestorCollection(ds, singleInvestor.getKey());
				}
				else if(singleInvestor.getValue().equals(CrunchbaseNamespace.FINANCIAL_ORG.getLabel().toString()))
				{
					FinancialOrgDatabaseService.populateFinancialOrgCollection(ds, singleInvestor.getKey());
				}
				else if(singleInvestor.getValue().equals(CrunchbaseNamespace.COMPANY.getLabel().toString()))
				{
					companyDatabaseServiceAsInvestor.populateSingleCompanyCollection(ds, singleInvestor.getKey());
				}
			}
			logger.debug("Persistence is completed for - " + permalink);
		}
		
		logger.debug("Starting normalization");
		Normalization.cacluclateNormalizedScoreForData();
		logger.debug("Normalization completed and persisted");
		
		logger.debug("Starting ROI calculation");
		ROI.cacluclateROIForInvestors();
		ROI.starRank();
		logger.debug("ROI calculation completed and persisted");
	}

	private static List<String> getCompanyPermlinks() 
	{
		List<String> companyPermalinks = new ArrayList<String>();
		List<String[]> allCompanies = new ArrayList<String[]>();
		CSVReader reader;
		
		try 
		{
			reader = new CSVReader(new FileReader(companyFileName));
			allCompanies = reader.readAll();
			
			for(String[] company : allCompanies)
			{
				companyPermalinks.add(company[1]);
			}
			reader.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Number of permalinks are - " + companyPermalinks.size());
		return companyPermalinks;
	}

	public static int getAngelListCallCounter() {
		return angelListCallCounter;
	}

	public static void setAngelListCallCounter(int angelListCallCounter) {
		DatabaseService.angelListCallCounter = angelListCallCounter;
	}
	
	public static void increaseTheCounter()
	{
		angelListCallCounter = angelListCallCounter + 1;
	}
}
