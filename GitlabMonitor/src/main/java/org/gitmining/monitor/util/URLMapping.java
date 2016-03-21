package org.gitmining.monitor.util;

public class URLMapping {
	/**
	 * show project commit page
	 */
	public static final String PROJECT_COMMIT = "/project/commit";
	/**
	 * get project commit data 
	 */
	public static final String PROJECT_COMMIT_RANGE = "/project/commit/range";
	/**
	 * show all projects commit info
	 */
	public static final String PROJECT_SUMMARY = "/project/summary";
	/**
	 * get all projects commit data
	 */
	public static final String PROJECT_SUMMARY_DATA = "/project/summary/data";
	
	/**
	 * show project event page
	 */
	@Deprecated
	public static final String PROJECT_EVENT = "/project/event";
	/**
	 * get project event data
	 */
	@Deprecated
	public static final String PROJECT_EVENT_RANGE = "/project/event/range";
	/**
	 * show project team member page
	 */
	public static final String PROJECT_TEAM = "/project/team";
	/**
	 * get project team team data
	 */
	public static final String PROJECT_TEAMMEMBER = "/project/teammember";
	
	/**
	 * get project comment data
	 */
	@Deprecated
	public static final String PROJECT_COMMENT = "/project/comment";
	/**
	 * submit project comment
	 */
	@Deprecated
	public static final String PROJECT_COMMENT_SUBMIT = "/project/comment/submit";
	/**
	 * show project score
	 */
	public static final String PROJECT_SCORE = "/project/score";
	/**
	 * add a column of project score item
	 */
	public static final String PROJECT_SCORE_ADD = "/project/score/add";
	/**
	 * change score for a specific project in one item 
	 */
	public static final String PROJECT_SCORE_CHANGE = "/project/score/change";
	/**
	 * disable an item
	 */
	public static final String PROJECT_SCORE_DELETE = "/project/score/delete";
	/**
	 * search team
	 */
	public static final String PROJECT_TEAM_SEARCH = "/project/team/search";
	/**
	 * show student commit page
	 */
	public static final String STUDENT_COMMIT = "/student/commit";
	/**
	 * get student commit data
	 */
	public static final String STUDENT_COMMIT_RANGE = "/student/commit/range";
	/**
	 * show all student commits info
	 */
	public static final String STUDENT_SUMMARY = "/student/summary";
	/**
	 * get all student commits data
	 */
	public static final String STUDENT_SUMMARY_DATA = "/student/summary/data";
	/**
	 * show student event page
	 */
	@Deprecated
	public static final String STUDENT_EVENT = "/student/event";
	/**
	 * get student event data
	 */
	@Deprecated
	public static final String STUDENT_EVENT_RANGE = "/student/event/range";
	/**
	 * get student comment data
	 */
	@Deprecated
	public static final String STUDENT_COMMENT = "/student/comment";
	/**
	 * submit student comment
	 */
	@Deprecated
	public static final String STUDENT_COMMENT_SUBMIT = "/student/comment/submit";
}
