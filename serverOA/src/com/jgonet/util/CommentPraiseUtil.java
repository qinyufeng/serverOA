package com.jgonet.util;

import java.util.HashMap;
import java.util.Map;

public class CommentPraiseUtil {
	/**
	 * 存放活动名称
	 * <p>key : activity_key</p>
	 * <p>value : 实际活动名称</p>
	 */
	public final static Map<String, String> activityName = new HashMap<String, String>();

	static {
		activityName.put("volte_key", "volteonLine");// volte在线办理
		activityName.put("enjoySeason_key", "enjoySeasonActivity");// volte在线办理
		activityName.put("jacketPage_key", "jacketPageActivity");//五月封页套
		activityName.put("juneActivityGo_key", "juneActivityGo");//六月封页套
		activityName.put("augustActivity_key", "augustActivity");//六月封页套
		activityName.put("schoolMarketPage_key", "schoolMarketPage");//六月封页套
		activityName.put("ternaryCeremony_key", "ternaryCeremony");//三重奏办理
		activityName.put("fourRites_key", "fourRites");//校园四重礼办理
		activityName.put("schoolWelcome_key", "schoolWelcome");//校园迎新
		activityName.put("septemberActivityGo_key", "septemberActivityGo");//九月封套页
		activityName.put("withfullsend_key", "withfullsend");//流量用满送
		activityName.put("octoberActivityGo_key", "octoberActivityGo");//十月封套页
		activityName.put("novemberActivityGo_key", "novemberActivityGo");//十一月封套页
		activityName.put("decemberActivityGo_key", "decemberActivityGo");//十二月封套页
		activityName.put("January18ActivityGo_key", "January18ActivityGo");//2018年一月封套页
		activityName.put("feb18ActivityGo_key", "feb18ActivityGo");//2018年二月封套页
	}
}
