package com.bjpow.crm.workbench.service.impl;

import com.bjpow.crm.utils.SqlSessionUtil;
import com.bjpow.crm.workbench.dao.ActivityDao;
import com.bjpow.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao= SqlSessionUtil.getSqlSession ().getMapper (ActivityDao.class);
}
