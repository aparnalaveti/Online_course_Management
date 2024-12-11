package com.klef.jfsd.springboot.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.klef.jfsd.springboot.model.Course;
import com.klef.jfsd.springboot.model.FContact;
import com.klef.jfsd.springboot.model.Faculty;
import com.klef.jfsd.springboot.model.Student;
import com.klef.jfsd.springboot.service.AdminService;
import com.klef.jfsd.springboot.service.CourseService;
import com.klef.jfsd.springboot.service.FContactService;
import com.klef.jfsd.springboot.service.FacultyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class FacultyController
{
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private FContactService fContactService;
	
	@Value("${spring.mail.username}") 
    private String adminMail;
	
	@GetMapping("faculty/faq")        // URI & method name can be different
    public ModelAndView faq()
	{
	   ModelAndView mv = new ModelAndView();
	   mv.setViewName("faculty/faq");
	   return mv;
	}
	
	@GetMapping("faculty/add-material")               
    public ModelAndView addmaterial()
	{
	   ModelAndView mv = new ModelAndView();
	   mv.setViewName("faculty/add-material");
	   return mv;
	}
		
	@GetMapping("faculty/about")               
    public ModelAndView about()
	{
	   ModelAndView mv = new ModelAndView();
	   mv.setViewName("faculty/about");
	   return mv;
	}
	
	@GetMapping("facultylogin") // URI & method name can be different
    public ModelAndView facultylogin()
	{
	   ModelAndView mv = new ModelAndView();
	   mv.setViewName("login");
	   return mv;
	}
	
	@PostMapping("checkfacultylogin")
	public ModelAndView checkfacultylogin(HttpServletRequest request)
	{
	     ModelAndView mv = new ModelAndView();
	     
	     String id = request.getParameter("username");
	     int fid=Integer.parseInt(id);
	     String pwd = request.getParameter("password");
	     
	     Faculty f = facultyService.checkfacultylogin(fid, pwd);
	       
	     if(f!=null)
	     {
	        //session
	    	 
	    	 HttpSession session=request.getSession();
	    	 session.setAttribute("fid", f.getId());  //eid is a session variable
	    	 session.setAttribute("fname", f.getFirstname()+" "+f.getLastname()); //ename is a session variable
	    	 
	    	//session
	        mv.setViewName("facultyhome");
	 
	     }
	     else
	     {
	        mv.setViewName("login");
	        mv.addObject("message", "Login Failed");
	     }
	       
	     return mv;
	}
	
	@GetMapping("facultyhome") // URI & method name can be different
    public ModelAndView facultyhome(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		
		int sid=(int)session.getAttribute("fid"); //eid is a session variable
		String sname=(String) session.getAttribute("fname"); //ename is a session variable
		
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("facultyhome");
	     
	    mv.addObject("fid", sid);
	    mv.addObject("fname", sname);
	    
	    return mv;
	}
	
	@GetMapping("faculty/contact")
  	public ModelAndView addfcontact()
  	{
  	   ModelAndView mv = new ModelAndView();
  	   mv.setViewName("faculty/contact");
  	   return mv;
  	}
    
    @PostMapping("faculty/insertfcontact")
	public ModelAndView insertfcontact(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		
		String msg=null;
		try
		{	
			String name = request.getParameter("fcontactname");
			String fromMail = request.getParameter("fcontactfmail");
			String phoneNo = request.getParameter("fcontactmobile");
			String subject = request.getParameter("fcontactsubject");
			String text = request.getParameter("fcontacttext");
			
			
			FContact fc=new FContact();
			fc.setName(name);
			fc.setFromMail(fromMail);
			fc.setPhoneNo(phoneNo);
			fc.setSubject(subject);
			fc.setText(text);
			
			msg = fContactService.addFContact(fc);
			
			fContactService.sendEmail(adminMail, fromMail, subject, text);  //faculty
			fContactService.sendEmailAd(adminMail, adminMail, subject, text); //admin
			
			mv.setViewName("faculty/displaymsg");
			mv.addObject("message",msg);
		}
		catch(Exception e)
		{
			msg=e.getMessage();
			mv.setViewName("faculty/displayerror");
			mv.addObject("message",msg);
		}
		return mv;
	}
    
    @GetMapping("faculty/profile") // URI & method name can be different
    public ModelAndView facultyprofile(HttpServletRequest request)
  {
    HttpSession session=request.getSession();
    
    int fid=(int)session.getAttribute("fid"); 
    String fname=(String) session.getAttribute("fname");
    
      ModelAndView mv = new ModelAndView();
      mv.setViewName("faculty/profile");
       
      mv.addObject("fid", fid);
      mv.addObject("fname", fname);
      Faculty f = adminService.viewfacultybyid(fid);
        
      mv.addObject("f", f);
      
      List<Course> courses=courseService.getCoursesByFacultyId(fid);
      mv.addObject("courses", courses);
      
      return mv;
  }
  
  @PostMapping("faculty/updatefacultyprofile")
    public ModelAndView updateaction4(HttpServletRequest request,@RequestParam("facultyimg") MultipartFile file) throws IOException, SerialException, SQLException
    {
      String msg = null;
      
      ModelAndView mv = new ModelAndView();
      
      HttpSession session = request.getSession();
      
      mv.addObject("fid", session.getAttribute("fid"));
      mv.addObject("fname", session.getAttribute("fname"));
      
      
     try
     {
    	 String fname = request.getParameter("facultyfname");
         String lname = request.getParameter("facultylname");
         String email = request.getParameter("facultyemail");
         String jod = request.getParameter("facultyjod");
         String pwd = request.getParameter("facultypass");
         String id = request.getParameter("facultyid");
         int fid=Integer.parseInt(id);
     
         String deg = request.getParameter("facultydeg");
         String gender = request.getParameter("facultygender");
         String mobile = request.getParameter("facultymobile");
         String dept = request.getParameter("facultydept");
         String sal = request.getParameter("facultysal");
         double fsal=Double.parseDouble(sal);
         
         String address = request.getParameter("facultyaddress");
         String dob = request.getParameter("facultydob");
         
         byte[] bytes = file.getBytes();
 		 Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
         
         Faculty f=new Faculty();
         f.setFirstname(fname);
         f.setLastname(lname);
         f.setEmail(email);
         f.setJoiningdate(jod);
         f.setPassword(pwd);
         f.setId(fid);
         f.setDesignation(deg);
         f.setGender(gender);
         f.setContact(mobile);
         f.setDepartment(dept);
         f.setSalary(fsal);
         f.setAddress(address);
         f.setDateofbirth(dob);
         f.setFacultyimg(blob);
        
      Faculty fac = adminService.viewfacultybyid(fid);
      f.setPassword(fac.getPassword());
      msg = adminService.updatefaculty(f);
        
        mv.setViewName("faculty/displaymsg");
        mv.addObject("message",msg);
       
     }
     catch(Exception e)
     {
        msg = e.getMessage();
       
        mv.setViewName("faculty/displayerror");
        mv.addObject("message",msg);
     }
   
  return mv;

 }
  
   @GetMapping("faculty/displayfacultyimage")
   public ResponseEntity<byte[]> displayfacultyimagedemo1(@RequestParam("id") int id) throws IOException, SQLException
   {
     Faculty faculty = adminService.viewfacultybyid(id);
     byte [] imageBytes = null;
     imageBytes = faculty.getFacultyimg().getBytes(1,(int) faculty.getFacultyimg().length());

     return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
  }
   
   @GetMapping("faculty/courses")
	public ModelAndView facultyviewallcourses(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("faculty/courses");
		
		HttpSession session=request.getSession();
		int fid=(int)session.getAttribute("fid"); 
		
		List<Course> courses=courseService.getCoursesByFacultyId(fid);
		
		mv.addObject("courses", courses);
		return mv;
	}
	
	@GetMapping("faculty/displaycourseimage")
	public ResponseEntity<byte[]> displaycourseimagedemo2(@RequestParam("id") int id) throws IOException, SQLException
	{
	  Course course = adminService.viewcoursebyid(id);
	  
	  byte [] imageBytes = null;
	  imageBytes = course.getCimage().getBytes(1,(int) course.getCimage().length());

	  return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}
	
   
   @GetMapping("faculty/logout")
   public ModelAndView logout1()
	  {
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("faculty/index");
	    mv.addObject("message", "Logout Successfully");
	    return mv;
	  }
   
   @PostMapping("faculty/updatefacultypassword")
   public ModelAndView updatefacultypassword(HttpServletRequest request)
   {
     String msg = null;
     
     ModelAndView mv = new ModelAndView();
     
     HttpSession session = request.getSession();
   
     int fid=(int)session.getAttribute("fid");
     mv.addObject("fid", session.getAttribute("fid"));
     mv.addObject("fname", session.getAttribute("fname"));
     
     
    try
    {
      Faculty fac = adminService.viewfacultybyid(fid);      
      String pwd = request.getParameter("currentpassword");
      String pwd1 = request.getParameter("newpassword");
      String pwd2 = request.getParameter("renewpassword");
  
      if(fac.getPassword().equals(pwd) && pwd1.equals(pwd2))
      {
        fac.setPassword(pwd1);
        msg = facultyService.updatepassword(fac);
         
        mv.setViewName("faculty/displaymsg");
        mv.addObject("message",msg); 
      }
      else
      {
        msg = "Error in Updating Password";
         
        mv.setViewName("faculty/displayerror");
        mv.addObject("message",msg); 
      }           
    }
    catch(Exception e)
    {
       msg = e.getMessage();
      
       mv.setViewName("faculty/displayerror");
       mv.addObject("message",msg);
    }
  
 return mv;

}
   
   @GetMapping("faculty/view-students")
   public ModelAndView viewallStudentsByCourse(HttpServletRequest request)
   {
     ModelAndView mv = new ModelAndView("faculty/view-students");
     
     HttpSession session=request.getSession();
     int fid=(int)session.getAttribute("fid");
     
     List<Course> courses=courseService.getCoursesByFacultyId(fid);
     mv.addObject("courses", courses);
     
     List<Student> s = facultyService.viewallStudentsByFaculty(fid);
     mv.addObject("students", s);
     
     return mv;
   }
	
   @GetMapping("faculty/displaystudents")
   public ModelAndView viewallStudentsByCourse1(@RequestParam("id") int cid,HttpServletRequest request)
   {
     ModelAndView mv = new ModelAndView("faculty/view-students");
     
     HttpSession session=request.getSession();
     int fid=(int)session.getAttribute("fid");
     
     List<Course> courses=courseService.getCoursesByFacultyId(fid);
     mv.addObject("courses", courses);
     
     List<Student> s = facultyService.viewallStudentsByCourse(fid, cid);
     mv.addObject("students", s);
     
     
     return mv;
   }
   
   @GetMapping("faculty/displaystudentimage")
   public ResponseEntity<byte[]> displaystudentimagedemo2(@RequestParam("id") int id) throws IOException, SQLException
   {
     Student student = adminService.viewstudentbyid(id);
     byte [] imageBytes = null;
     imageBytes = student.getStudentimg().getBytes(1,(int) student.getStudentimg().length());

     return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
   }
   
}
