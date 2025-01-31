package com.hr.hr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr.models.LeaveDetails;
import com.hr.hr.repository.LeaveManageNativeSqlRepo;
import com.hr.hr.repository.LeaveManageRepository;

@Service(value = "leaveManageService")
public class LeaveManageService {

    @Autowired
    private LeaveManageRepository leaveManageRepository;

    @Autowired
    private LeaveManageNativeSqlRepo leaveManageNativeRepo;

    @SuppressWarnings("deprecation")
    public void applyLeave(LeaveDetails leaveDetails) {

	int duration = leaveDetails.getToDate().getDate() - leaveDetails.getFromDate().getDate();
	leaveDetails.setDuration(duration + 1);
	leaveDetails.setActive(true);
	leaveManageRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getAllLeaves() {

	return leaveManageRepository.findAll();
    }

    public LeaveDetails getLeaveDetailsOnId(int id) {

	return leaveManageRepository.findOneById(id);
    }

    public void updateLeaveDetails(LeaveDetails leaveDetails) {

	leaveManageRepository.save(leaveDetails);

    }

    public List<LeaveDetails> getAllActiveLeaves() {

	return leaveManageRepository.getAllActiveLeaves();
    }

    public List<LeaveDetails> getAllLeavesOfUser(String username) {

	return leaveManageRepository.getAllLeavesOfUser(username);

    }

    public List<LeaveDetails> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected) {

        StringBuilder whereQuery = new StringBuilder(); 
        if (pending)
            whereQuery.append("active=true or ");
        if (accepted)
            whereQuery.append("(active=false and accept_reject_flag=true) or ");
        if (rejected)
            whereQuery.append("(active=false and accept_reject_flag=false) or ");
    
        whereQuery.append(" 1=0"); 
    
        return leaveManageNativeRepo.getAllLeavesOnStatus(whereQuery.toString()); 
    }
    
}
