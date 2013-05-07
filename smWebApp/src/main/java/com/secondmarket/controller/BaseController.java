package com.secondmarket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.secondmarket.batch.CompanyFilters;
import com.secondmarket.batch.CompanyService;
import com.secondmarket.batch.FinancialOrgService;
import com.secondmarket.batch.InvestorFilters;
import com.secondmarket.batch.InvestorService;
import com.secondmarket.batch.RankCompany;
import com.secondmarket.batch.RankInvestor;
import com.secondmarket.common.CrunchbaseNamespace;
import com.secondmarket.domain.Company;
import com.secondmarket.domain.Financial_Org;
import com.secondmarket.domain.Investor;

@Controller
@RequestMapping("/")

public class BaseController 
{
	protected static Logger logger = Logger.getLogger("controller");
	
	int recordsPerPage = 50;
	 
	@Resource(name="investorService")
	private InvestorService investorService;
	
	@Resource(name="companyService")
	private CompanyService companyService;
	
	@Resource(name="financialOrgService")
	private FinancialOrgService financialOrgService;
	
	@Resource(name="rankingService")
	private RankInvestor rankedInvestor;
	
	@Resource(name="companyRankingService")
	private RankCompany rankCompany;
	
	@Resource(name="companyFilterService")
	private CompanyFilters companyFilterService;
	
	@Resource(name="investorFilterService")
	private InvestorFilters investorFilterService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String welcome(ModelMap model) 
	{
		model.addAttribute("message", "Welcome!");
		return "index"; 
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String welcomeHome(ModelMap model) {
 
		model.addAttribute("message", "Welcome Home");
		return "index";
	}
	
	//*************************************** Summary pages for Company, Investor and Fin Org ************************************************//
	
	@RequestMapping(value="/companies", method = RequestMethod.GET)
	public String getCompanies(@RequestParam("page") int page, ModelMap model) 
	{
		logger.debug("Received request to show all companies");
		int pageNumber = 1;
        pageNumber = page;
		List<Company> companies = companyService.getAllCompanies();
		int noOfRecords = companies.size();
    	logger.debug("Total companies are - " + companies.size());
    	int startIndex= (pageNumber-1)*recordsPerPage;
    	int endIndex = (noOfRecords - startIndex)>recordsPerPage ? (startIndex + recordsPerPage) : (startIndex + noOfRecords - startIndex);
    	List<Company> list = companies.subList(startIndex, endIndex);
    	int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
    	model.addAttribute("noOfPages", noOfPages);
    	model.addAttribute("companies", list);
    	model.addAttribute("startIndex", startIndex+1);
    	model.addAttribute("endIndex", endIndex);
    	model.addAttribute("size", noOfRecords);
    	model.addAttribute("currentPage", page);
      	model.addAttribute("periods", "3");
    	return "companyPage";
	}
	
	@RequestMapping(value="/investors", method = RequestMethod.GET)
	public String getInvestors(@RequestParam("page") int page, ModelMap model) 
	{
		logger.debug("Received request to show all investors");	
		int pageNumber = 1;
        pageNumber = page;
		List<Investor> investors = investorService.getAllInvestors();
		int noOfRecords = investors.size();
    	logger.debug("Totat individual investors are - " + investors.size());
    	int startIndex= (pageNumber-1)*recordsPerPage;
    	int endIndex = (noOfRecords - startIndex)>recordsPerPage ? (startIndex + recordsPerPage) : (startIndex + noOfRecords - startIndex);
    	List<Investor> list = investors.subList(startIndex, endIndex);
    	int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
    	model.addAttribute("noOfPages", noOfPages);
    	model.addAttribute("investors", list);
    	model.addAttribute("startIndex", startIndex+1);
    	model.addAttribute("endIndex", endIndex);
    	model.addAttribute("size", noOfRecords);
    	model.addAttribute("currentPage", page);
    	model.addAttribute("followerLevel", "3");
    	model.addAttribute("companyLevel", "3");
    	model.addAttribute("roiLevel", "3");
    	return "investorsPage";
	}
	
	@RequestMapping(value="/financialOrg", method = RequestMethod.GET)
	public String getFinancialOrg(@RequestParam("page") int page, ModelMap model) 
	{
		int pageNumber = 1;
        pageNumber = page;
		logger.debug("Received request to show all financial org");
    	
		List<Financial_Org> finOrgs = financialOrgService.getAllFinancialOrgs();
		int noOfRecords = finOrgs.size();
    	logger.debug("Totat financial orgs are - " + finOrgs.size());
    	int startIndex= (pageNumber-1)*recordsPerPage;
    	int endIndex = (noOfRecords - startIndex)>recordsPerPage ? (startIndex + recordsPerPage) : (startIndex + noOfRecords - startIndex);
    	List<Financial_Org> list = finOrgs.subList(startIndex, endIndex);
    	int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
    	model.addAttribute("noOfPages", noOfPages);
    	model.addAttribute("finOrgs", list);
    	model.addAttribute("startIndex", startIndex+1);
    	model.addAttribute("endIndex", endIndex);
    	model.addAttribute("size", noOfRecords);
    	model.addAttribute("currentPage", page);
    	return "financialOrgPage";
	}
	
	//*************************************************** Ranking Methods ********************************************************//
	
	@RequestMapping(value="/investorRankingByFC_CC_ROI", method = RequestMethod.POST)
	public String getInvestorRanking(@RequestParam("followersImpLevel") String followersImpLevel, 
			@RequestParam("companyImpLevel") String companyImpLevel, @RequestParam("roiImpLevel") String roiImpLevel, ModelMap model) 
	{
		logger.debug("Received request to rank investors, weight on followers, value = " + followersImpLevel);
		logger.debug("Received request to rank investors, weight on companies invested in, value = " + companyImpLevel);
		logger.debug("Received request to rank investors, weight on roi, value = " + roiImpLevel);
    	
		List<Investor> investors = rankedInvestor.getSortedInvestorBasedOnFC_CC_ROI(followersImpLevel, companyImpLevel, roiImpLevel);
    	logger.debug(investors.size());
    	
    	model.addAttribute("investors", investors);
    	model.addAttribute("followerLevel", followersImpLevel);
    	model.addAttribute("companyLevel", companyImpLevel);
    	model.addAttribute("roiLevel", roiImpLevel);
    	return "investorsPage";
	}
	
	@RequestMapping(value="/finOrgRankingByFC_CC_ROI", method = RequestMethod.POST)
	public String getFinancialOrgRanking(@RequestParam("followersImpLevel") String followersImpLevel, 
			@RequestParam("companyImpLevel") String companyImpLevel, @RequestParam("roiImpLevel") String roiImpLevel, ModelMap model) 
	{
		logger.debug("Received request to rank fin org, weight on followers, value = " + followersImpLevel);
		logger.debug("Received request to rank fin org, weight on companies invested in, value = " + companyImpLevel);
		logger.debug("Received request to rank fin org, weight on roi, value = " + roiImpLevel);
    	
		List<Financial_Org> financial_Orgs = rankedInvestor.getSortedFinanciaOrgBasedOnFC_CC_ROI(followersImpLevel, companyImpLevel, roiImpLevel);
    	logger.debug(financial_Orgs.size());
    	
    	model.addAttribute("finOrgs", financial_Orgs);
    	model.addAttribute("followerLevel", followersImpLevel);
    	model.addAttribute("companyLevel", companyImpLevel);
    	model.addAttribute("roiLevel", roiImpLevel);
    	return "financialOrgPage";
	}
	
	@RequestMapping(value="/companyRankingByFundTime", method = RequestMethod.POST)
	public String getCompanyRankingByFundTime(@RequestParam("periodPast") String periodPast, ModelMap model) 
	{
		logger.debug("Received request to rank companies, by fund time, value = " + periodPast);
    	
		List<Company> companies = rankCompany.companyRankingByFundTime(periodPast);
    	
    	model.addAttribute("companies", companies);
    	model.addAttribute("periods", periodPast);
    	return "companyPage";
	}
	
	@RequestMapping(value="/companyRankingByFollowers", method = RequestMethod.POST)
	public String getCompanyRankedByFollowers(@RequestParam("comfollowersImpLevel") String comfollowersImpLevel, ModelMap model) 
	{
		logger.debug("Received request to rank company, weight on followers, value = " + comfollowersImpLevel);
    	
		List<Company> companies = rankCompany.getSortedCompanyBasedOnFC(comfollowersImpLevel);
    	logger.debug(companies.size());
    	
    	model.addAttribute("companies", companies);
    	model.addAttribute("comfollowersImpLevel", comfollowersImpLevel);
    	return "companyPage";
	}
	
	//*************************************************** Filter Methods ***********************************************************//
	
	@RequestMapping(value="/companyFundFilter",  method = RequestMethod.POST)
	public String filterCompanyByFund(@RequestParam("total_funding") String checkBoxVal, ModelMap model) 
	{
		logger.debug("Received request to filter company, by total funds raised, value = " + checkBoxVal);
		
		String[] parts = checkBoxVal.split(",");
    	List<Company> companies = companyFilterService.filterByFunds(parts);
    	
    	model.addAttribute("companies", companies);
    	model.addAttribute("total_funding", checkBoxVal);
    	return "companyPage";
	}
		
	@RequestMapping(value="/starsFilter",  method = RequestMethod.POST)
	public String getInvestorByStar(@RequestParam("starLevel") String starLevel, ModelMap model) 
	{
		logger.debug("Received request to filter investor, by star, value = " + starLevel);
		List<Investor> investors = investorFilterService.filterByStar(starLevel);
    	
    	model.addAttribute("investors", investors);
    	model.addAttribute("starl", starLevel);
    	return "investorsPage";
	}
	
	@RequestMapping(value="/starsFilterFin",  method = RequestMethod.POST)
	public String getFinByStar(@RequestParam("starLevel") String starLevel, ModelMap model) 
	{
		logger.debug("Received request to filter institution investor, by star, value = " + starLevel);
		List<Financial_Org> financialOrg = investorFilterService.filterByStarFin(starLevel);
    	
    	model.addAttribute("finOrgs", financialOrg);
    	model.addAttribute("starl", starLevel);
    	return "financialOrgPage";
	}
	
	@RequestMapping(value="/companyLocationFilter",  method = RequestMethod.POST)
	public String filterCompanyByLocation(@RequestParam("location") String checkBoxVal, ModelMap model) 
	{
		
		logger.debug("Received request to filter company, by location, value = " + checkBoxVal);
		String[] parts = checkBoxVal.split(",");
    	List<Company> companies = companyFilterService.filterByLocation(parts);
    	
    	model.addAttribute("companies", companies);
    	model.addAttribute("location", checkBoxVal);
    	return "companyPage";
	}
	
	@RequestMapping(value="/investorLocationFilter",  method = RequestMethod.POST)
	public String filterInvestorByLocation(@RequestParam("location") String checkBoxVal, ModelMap model) 
	{
		logger.debug("Received request to filter investors, by location, value = " + checkBoxVal);
		String[] parts = checkBoxVal.split(",");
    	List<Investor> investors = investorFilterService.filterIndividualInvstorsByLocation(parts);
    	
    	model.addAttribute("investors", investors);
    	model.addAttribute("location", checkBoxVal);
    	return "investorsPage";
	}
	
	@RequestMapping(value="/finOrgLocationFilter",  method = RequestMethod.POST)
	public String filterFinancialOrgByLocation(@RequestParam("location") String checkBoxVal, ModelMap model) 
	{
		logger.debug("Received request to filter institutional investors, by location, value = " + checkBoxVal);
		String[] parts = checkBoxVal.split(",");
    	List<Financial_Org> finOrgs = investorFilterService.filterInstitutionalInvstorsByLocation(parts);
    	
    	model.addAttribute("finOrgs", finOrgs);
    	model.addAttribute("location", checkBoxVal);
    	return "financialOrgPage";
	}
	
	//*************************************** Detailed pages for COmpany, Investor and Fin Org ************************************************//
	
	@RequestMapping(value="/investorProfile", method = RequestMethod.GET)
	public String getInvestorProfile(@RequestParam("permalink") String permalink, ModelMap model) 
	{
		logger.debug("Received request to show investors detailed profile");
		
		Investor investor = investorService.getInvestor(permalink);
		List<Company> companiesInvestedIn = companyService.getCompaniesGivenPermalinks(investor.getCompaniesInvestedIn());
		
		model.addAttribute("investor", investor);
		model.addAttribute("companies", companiesInvestedIn);
    	return "investorProfile";
	}
	
	@RequestMapping(value="/companyProfile", method = RequestMethod.GET)
	public String getCompanyProfile(@RequestParam("permalink") String permalink, ModelMap model) 
	{
		logger.debug("Received request to show company detailed profile");
		Company company = companyService.getCompany(permalink);
		
		Map<String, List<String>> categorizedPermlinks = separateTypeOfInvestor(company.getInvestorPermalinks());
		List<Investor> personInvested = investorService.getInvestorsGivenPermalinks(categorizedPermlinks.get(CrunchbaseNamespace.PERSON.getLabel().toString()));
		List<Company> companyInvested = companyService.getCompaniesGivenPermalinks(categorizedPermlinks.get(CrunchbaseNamespace.COMPANY.getLabel().toString()));
		List<Financial_Org> finOrgInvested = financialOrgService.getFinancialOrgsGivenPermalinks(categorizedPermlinks.get(CrunchbaseNamespace.FINANCIAL_ORG.getLabel().toString()));
		
		model.addAttribute("company", company);
		model.addAttribute("personInvested", personInvested);
		model.addAttribute("companyInvested", companyInvested);
		model.addAttribute("finOrgInvested", finOrgInvested);
    	return "companyProfile";
	}
	
	@RequestMapping(value="/financialOrgProfile", method = RequestMethod.GET)
	public String getFinancialOrgProfile(@RequestParam("permalink") String permalink, ModelMap model) 
	{
		logger.debug("Received request to show financial orgs detailed profile");
		
		Financial_Org finOrg = financialOrgService.getFinancialOrg(permalink);
		List<Company> companiesInvestedIn = companyService.getCompaniesGivenPermalinks(finOrg.getCompaniesInvestedIn());
		
		model.addAttribute("finOrg", finOrg);
		model.addAttribute("companies", companiesInvestedIn);
    	return "financialOrgProfilePage";
	}
	
	private Map<String, List<String>> separateTypeOfInvestor(Map<String, String> investorPermalinks)
	{
		Map<String, List<String>> categorizedPermlinks = new HashMap<String, List<String>>();
		if(investorPermalinks != null && !investorPermalinks.isEmpty())
		{
			for(Entry<String, String> pair : investorPermalinks.entrySet())
			{
				if(pair.getValue().equals(CrunchbaseNamespace.PERSON.getLabel().toString()))
				{
					if(!categorizedPermlinks.containsKey(CrunchbaseNamespace.PERSON.getLabel().toString()))
					{
						List<String> permalinks = new ArrayList<String>();
						permalinks.add(pair.getKey());
						categorizedPermlinks.put(CrunchbaseNamespace.PERSON.getLabel().toString(), permalinks);
					}
					else
					{
						categorizedPermlinks.get(CrunchbaseNamespace.PERSON.getLabel().toString()).add(pair.getKey());
					}
				}
				else if(pair.getValue().equals(CrunchbaseNamespace.FINANCIAL_ORG.getLabel().toString()))
				{
					if(!categorizedPermlinks.containsKey(CrunchbaseNamespace.FINANCIAL_ORG.getLabel().toString()))
					{
						List<String> permalinks = new ArrayList<String>();
						permalinks.add(pair.getKey());
						categorizedPermlinks.put(CrunchbaseNamespace.FINANCIAL_ORG.getLabel().toString(), permalinks);
					}
					else
					{
						categorizedPermlinks.get(CrunchbaseNamespace.FINANCIAL_ORG.getLabel().toString()).add(pair.getKey());
					}
				}
				else if(pair.getValue().equals(CrunchbaseNamespace.COMPANY.getLabel().toString()))
				{
					if(!categorizedPermlinks.containsKey(CrunchbaseNamespace.COMPANY.getLabel().toString()))
					{
						List<String> permalinks = new ArrayList<String>();
						permalinks.add(pair.getKey());
						categorizedPermlinks.put(CrunchbaseNamespace.COMPANY.getLabel().toString(), permalinks);
					}
					else
					{
						categorizedPermlinks.get(CrunchbaseNamespace.COMPANY.getLabel().toString()).add(pair.getKey());
					}
				}
			}
		}
		return categorizedPermlinks;
	}
}
