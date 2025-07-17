package com.sdmsproject.sdms.Repository;

import java.sql.Date;

public interface CustomAttendanceProjection {
	    String getAttendance();
	    Long getClassId();
	    Date getDate();
	    Long getStudentId();
	    String getStuClass();
	    String getStuFirstName();
	    String getStuLastName();
	}
