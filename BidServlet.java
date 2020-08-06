package com.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.model.*;

public class BidServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException{
		doPost(req,res);
	}
	
public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
	req.setCharacterEncoding("UTF-8");
	String action =req.getParameter("action");
	
	if("getOne_For_Display".equals(action)) {		
		List<String>errorMsgs=new LinkedList<String>();
		req.setAttribute("errorMsgs",errorMsgs);
		try {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			String str = req.getParameter("mem_no");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("�п�J���u�s��");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
				return;//�{�����_
			}
			
			Integer mem_no = null;
			try {
				mem_no = new Integer(str);
			} catch (Exception e) {
				errorMsgs.add("���u�s���榡�����T");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
				return;//�{�����_
			}
			
			/***************************2.�}�l�d�߸��*****************************************/
			BidService bidSvc = new BidService();
			BidVO bidVO = bidSvc.getOneBid(mem_no);
			if (bidVO == null) {
				errorMsgs.add("�d�L���");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
				return;//�{�����_
			}
			
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
			req.setAttribute("bidVO", bidVO); // ��Ʈw���X��empVO����,�s�Jreq
			String url = "/bid/listOneEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
			successView.forward(req, res);

			/***************************��L�i�઺���~�B�z*************************************/
		} catch (Exception e) {
			errorMsgs.add("�L�k���o���:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/select_page.jsp");
			failureView.forward(req, res);
		}
	}
	
	
	if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {
			/***************************1.�����ШD�Ѽ�****************************************/
			Integer mem_no = new Integer(req.getParameter("mem_no"));
			
			/***************************2.�}�l�d�߸��****************************************/
			BidService bidSvc = new BidService();
			BidVO bidVO = bidSvc.getOneBid(mem_no);
							
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
			req.setAttribute("bidVO", bidVO);         // ��Ʈw���X��empVO����,�s�Jreq
			String url = "/emp/update_emp_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
			successView.forward(req, res);

			/***************************��L�i�઺���~�B�z**********************************/
		} catch (Exception e) {
			errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/listAllEmp.jsp");
			failureView.forward(req, res);
		}
	}
	
	
	if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
		
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
	
		try {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			Integer mem_no = new Integer(req.getParameter("mem_no").trim());
			
			String ename = req.getParameter("ename");
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (ename == null || ename.trim().length() == 0) {
				errorMsgs.add("���u�m�W: �ФŪť�");
			} else if(!ename.trim().matches(enameReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
				errorMsgs.add("���u�m�W: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
            }
			
			String job = req.getParameter("job").trim();
			if (job == null || job.trim().length() == 0) {
				errorMsgs.add("¾��ФŪť�");
			}	
			
			java.sql.Date hiredate = null;
			try {
				hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
			} catch (IllegalArgumentException e) {
				hiredate=new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("�п�J���!");
			}

			Double sal = null;
			try {
				sal = new Double(req.getParameter("sal").trim());
			} catch (NumberFormatException e) {
				sal = 0.0;
				errorMsgs.add("�~���ж�Ʀr.");
			}

			Double comm = null;
			try {
				comm = new Double(req.getParameter("comm").trim());
			} catch (NumberFormatException e) {
				comm = 0.0;
				errorMsgs.add("�����ж�Ʀr.");
			}

			Integer deptno = new Integer(req.getParameter("deptno").trim());
			Integer BP_NO = new Integer(req.getParameter("BP_NO").trim());
			String BR_PROJECT = new String(req.getParameter("BR_PROJECT").trim());
			String BR_CONTENT = new String(req.getParameter("BR_CONTENT").trim());
			java.sql.Date BR_TIME = java.sql.Date.valueOf(req.getParameter("BR_TIME").trim());
			Integer BAP_STATUS = new Integer(req.getParameter("BAP_STATUS").trim());

			BidVO bidVO = new BidVO();
			bidVO.setMEM_NO(mem_no);
			bidVO.setBP_NO(BP_NO);
			bidVO.setBR_PROJECT(BR_PROJECT);
			bidVO.setBR_CONTENT(BR_CONTENT);
			bidVO.setBR_TIME(BR_TIME);
			bidVO.setBAP_STATUS(BAP_STATUS);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("bidVO", bidVO); // �t����J�榡���~��empVO����,�]�s�Jreq
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/update_emp_input.jsp");
				failureView.forward(req, res);
				return; //�{�����_
			}
			
			/***************************2.�}�l�ק���*****************************************/
			BidService bidSvc = new BidService();
			bidVO = bidSvc.updateBid(mem_no, BP_NO, BR_PROJECT, BR_CONTENT, BR_TIME, BAP_STATUS);
			
			/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
			req.setAttribute("empVO", bidVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
			String url = "/emp/listOneEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
			successView.forward(req, res);

			/***************************��L�i�઺���~�B�z*************************************/
		} catch (Exception e) {
			errorMsgs.add("�ק��ƥ���:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/update_emp_input.jsp");
			failureView.forward(req, res);
		}
	}

    if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
		
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
			String ename = req.getParameter("ename");
			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (ename == null || ename.trim().length() == 0) {
				errorMsgs.add("���u�m�W: �ФŪť�");
			} else if(!ename.trim().matches(enameReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
				errorMsgs.add("���u�m�W: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
            }
			
			String job = req.getParameter("job").trim();
			if (job == null || job.trim().length() == 0) {
				errorMsgs.add("¾��ФŪť�");
			}
			
			java.sql.Date hiredate = null;
			try {
				hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
			} catch (IllegalArgumentException e) {
				hiredate=new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("�п�J���!");
			}
			
			Double sal = null;
			try {
				sal = new Double(req.getParameter("sal").trim());
			} catch (NumberFormatException e) {
				sal = 0.0;
				errorMsgs.add("�~���ж�Ʀr.");
			}
			
			Double comm = null;
			try {
				comm = new Double(req.getParameter("comm").trim());
			} catch (NumberFormatException e) {
				comm = 0.0;
				errorMsgs.add("�����ж�Ʀr.");
			}
			
			Integer deptno = new Integer(req.getParameter("deptno").trim());

			BidVO bidVO = new BidVO();
			Integer mem_no = new Integer(req.getParameter("deptno").trim());
			Integer BP_NO = new Integer(req.getParameter("BP_NO").trim());
			String BR_PROJECT = new String(req.getParameter("BR_PROJECT").trim());
			String BR_CONTENT = new String(req.getParameter("BR_CONTENT").trim());
			java.sql.Date BR_TIME = java.sql.Date.valueOf(req.getParameter("BR_TIME").trim());
			Integer BAP_STATUS = new Integer(req.getParameter("BAP_STATUS").trim());

			

		
			bidVO.setMEM_NO(mem_no);
			bidVO.setBP_NO(BP_NO);
			bidVO.setBR_PROJECT(BR_PROJECT);
			bidVO.setBR_CONTENT(BR_CONTENT);
			bidVO.setBR_TIME(BR_TIME);
			bidVO.setBAP_STATUS(BAP_STATUS);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("bidVO", bidVO); // �t����J�榡���~��empVO����,�]�s�Jreq
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/addEmp.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.�}�l�s�W���***************************************/
			BidService bidSvc = new BidService();
			bidVO = bidSvc.addEmp(mem_no, BP_NO, BR_PROJECT, BR_CONTENT, BR_TIME, BAP_STATUS);
			
			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
			String url = "/emp/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
			successView.forward(req, res);				
			
			/***************************��L�i�઺���~�B�z**********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/addEmp.jsp");
			failureView.forward(req, res);
		}
	}
	
	
	if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/***************************1.�����ШD�Ѽ�***************************************/
			Integer mem_no = new Integer(req.getParameter("mem_no"));
			
			/***************************2.�}�l�R�����***************************************/
			BidService bidSvc = new BidService();
			bidSvc.deleteBid(mem_no);
			
			/***************************3.�R������,�ǳ����(Send the Success view)***********/								
			String url = "/emp/listAllEmp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
			successView.forward(req, res);
			
			/***************************��L�i�઺���~�B�z**********************************/
		} catch (Exception e) {
			errorMsgs.add("�R����ƥ���:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/emp/listAllEmp.jsp");
			failureView.forward(req, res);
		}
	}
}
	
}
	
	

