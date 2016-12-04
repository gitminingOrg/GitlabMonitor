package org.gitmining.monitor.controller;

import static org.gitmining.monitor.util.URLMapping.PROJECT_COMMIT;
import static org.gitmining.monitor.util.URLMapping.PROJECT_COMMIT_RANGE;
import static org.gitmining.monitor.util.URLMapping.PROJECT_SUMMARY;
import static org.gitmining.monitor.util.URLMapping.PROJECT_SUMMARY_DATA;
import static org.gitmining.monitor.util.URLMapping.PROJECT_TEAM;
import static org.gitmining.monitor.util.URLMapping.PROJECT_TEAMMEMBER;
import static org.gitmining.monitor.util.URLMapping.PROJECT_TEAM_SEARCH;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.bean.Student;
import org.gitmining.monitor.bean.TeamVO;
import org.gitmining.monitor.service.ProjectService;
import org.gitmining.monitor.util.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * project related controllers, also see 2 more relevant ones in TestController implemented by zhaoyufeng
 * @author owenchen
 *
 */
@RestController
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	/**api 1
	 * mapping to the Project Summary Page, showing the summary statistics for each repository
	 * @param request
	 * @param response ModelAndView for project summary
	 * @return
	 */
	@RequestMapping(value=PROJECT_SUMMARY)
	public ModelAndView showAllProjectSummary(HttpServletRequest request,HttpServletResponse response){
		//start date and end date for the querying summary
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		
		//commitOrder : order by which field.  method: in decreasing or increasing order
		String commitOrder = request.getParameter("commitOrder");
		String method = request.getParameter("method");
		
		//course name
		String course = request.getParameter("course");
		//formula is supposed to be an expression for value calculation using the variables showing on page
		String formula = request.getParameter("formula");
		//filter courses
		String filter = request.getParameter("filter");
		
		if(dayStart == null){
			dayStart = "2016-01-01";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		ResultMap commitResult = projectService.projectCommitByCourse(course,dayStart, dayEnd,commitOrder,method, formula, filter);
		ModelAndView result = new ModelAndView("projectSummary");
		
		result.addObject("commits", commitResult.get("commits"));
		result.addObject("formula", formula);
		result.addObject("filter", filter);
		result.addObject("dayStart", dayStart);
		result.addObject("dayEnd", dayEnd);
		return result;
	}
	
	/**api 2
	 * only the data for the summary statistics of each repository, similar to api 1
	 * @param request
	 * @param respons
	 * @return
	 */
	@RequestMapping(value=PROJECT_SUMMARY_DATA)
	public Map<String, Object> ProjectCommitSummaryData(HttpServletRequest request,HttpServletResponse respons){
		Map<String, Object> result = new HashMap<String, Object>();
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String commitOrder = request.getParameter("commitOrder");
		String course = request.getParameter("course");
		String formula = request.getParameter("formula");
		String filter = request.getParameter("filter");
		String timeRange = request.getParameter("range");
		if(timeRange != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			dayEnd = sdf.format(calendar.getTime());
			
			if(timeRange.equals("year")){
				calendar.add(Calendar.YEAR, -1);
			}else if(timeRange.equals("month")){
				calendar.add(Calendar.MONTH, -1);
			}else if(timeRange.equals("week")){
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}
			dayStart = sdf.format(calendar.getTime());
		}
		String method = "desc";
		if(dayStart == null){
			dayStart = "2016-01-01";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		ResultMap commitResult = projectService.projectCommitByCourse(course,dayStart, dayEnd,commitOrder,method, formula, filter);
		result.put("commits", commitResult.get("commits"));
		result.put("formula", formula);
		result.put("filter", filter);
		result.put("dayStart", dayStart);
		result.put("dayEnd", dayEnd);
		
		return result;
	}
	
	/**api 3
	 * mapping to the Project Commit page, showing the detail info of a specific project
	 * @param request contains teamname, start date and end date
	 * @param response teaminfo, students and projects
	 * @return
	 */
	@RequestMapping(value=PROJECT_COMMIT)
	public ModelAndView showProjectCommitPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("projectCommit");
		String team = request.getParameter("team");
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		
		
		List<Student> students = projectService.getTeamStudent(team);
		List<ProjectVO> projects = projectService.getTeamProject(team);
		TeamVO teaminfo = projectService.getTeamInfo(team);
		
		if(team != null){
			result.addObject("team", team);
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			dayEnd = sdf.format(calendar.getTime());
			calendar.set(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			dayStart = sdf.format(calendar.getTime());
		}
		int projectId = 0;
		try{
			projectId = Integer.parseInt(request.getParameter("id"));
		}catch (Exception e) {
			if(projects.size() != 0){
				projectId = projects.get(projects.size()-1).getId();
			}
		}
		result.addObject("dayStart", dayStart);
		result.addObject("dayEnd", dayEnd);
		result.addObject("students", students);
		result.addObject("projects", projects);
		result.addObject("teaminfo", teaminfo);
		result.addObject("projectId", projectId);
		return result;
	}
	
	/**api 4
	 * providing the commit info needed by line chart
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=PROJECT_COMMIT_RANGE)
	public Map<String, Object> getProjectCommitItemRange(HttpServletRequest request,HttpServletResponse response){
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String timeRange = request.getParameter("timeRange");
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		if(timeRange != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			dayEnd = sdf.format(calendar.getTime());
			
			if(timeRange.equals("year")){
				calendar.add(Calendar.YEAR, -1);
			}else if(timeRange.equals("month")){
				calendar.add(Calendar.MONTH, -1);
			}else if(timeRange.equals("week")){
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}
			dayStart = sdf.format(calendar.getTime());
		}
		Map<String, Object> result = projectService.getProjectCommitItem(projectId, dayStart, dayEnd);
		result.put("dayStart", dayStart);
		result.put("dayEnd", dayStart);
		return result;
	}

	/**api 5
	 * decommissioned, used to show to team info 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=PROJECT_TEAM)
	public ModelAndView showProjectTeamPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("projectMember");
		String team = request.getParameter("team");
		if(team != null){
			result.addObject("team", team);
		}
		return result;
	}	
	
	/**api 6
	 * bar chart for the team member commit info
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=PROJECT_TEAMMEMBER)
	public ResultMap selectTeamStudentCommitRange(HttpServletRequest request, HttpServletResponse response){
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String timeRange = request.getParameter("timeRange");
		if(timeRange != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			dayEnd = sdf.format(calendar.getTime());
			
			if(timeRange.equals("year")){
				calendar.add(Calendar.YEAR, -1);
			}else if(timeRange.equals("month")){
				calendar.add(Calendar.MONTH, -1);
			}else if(timeRange.equals("week")){
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}
			dayStart = sdf.format(calendar.getTime());
		}
		if(dayStart == null){
			dayStart = "2016-01-01";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		ResultMap result =  projectService.selectTeamStudentCommitRange(projectId, dayStart, dayEnd);
		result.add("dayStart", dayStart);
		result.add("dayEnd", dayEnd);
		return result;
	}
	
	/**api 7
	 * matching the possible teams according to the input name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=PROJECT_TEAM_SEARCH)
	public ResultMap searchPossibleTeams(HttpServletRequest request, HttpServletResponse response){
		String possible_name = request.getParameter("possible_name");
		List<TeamVO> teamVOs = projectService.getLikeTeams(possible_name);
		ResultMap resultMap = new ResultMap();
		if(teamVOs == null || teamVOs.size() == 0){
			resultMap.setStatus(ResultMap.FAIL_STATUS);
			resultMap.setInfo("Sory, no matched team name!");
		}else {
			resultMap.setStatus(ResultMap.SUCCESS_STATUS);
			resultMap.add("teams", teamVOs);
		}
		return resultMap;
	}
}


