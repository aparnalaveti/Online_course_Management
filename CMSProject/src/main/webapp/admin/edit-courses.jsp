<%@ page contentType="text/html; charset=ISO-8859-1" isELIgnored="false"%> 
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<html lang="en">

<head>
	
     <%@ include file="adminheader.jsp" %>

</head>

<body>

    <!--*******************
        Preloader start
    ********************-->
    <div id="preloader">
        <div class="sk-three-bounce">
            <div class="sk-child sk-bounce1"></div>
            <div class="sk-child sk-bounce2"></div>
            <div class="sk-child sk-bounce3"></div>
        </div>
    </div>
    <!--*******************
        Preloader end
    ********************-->

    <!--**********************************
        Main wrapper start
    ***********************************-->
    <div id="main-wrapper">

        
        <!-- AdminNavbar Start -->

     <!-- adminheader1 navbar in adminpages -->
     <%@ include file="adminheader1.jsp" %>
        
        <!-- AdminNavbar End -->


        <!-- Sidebar -->
     <%@ include file="adminsidebar.jsp" %>

		
		
        <!--**********************************
            Content body start
        ***********************************-->
        <div class="content-body">
            <!-- row -->
            <div class="container-fluid">
				    
                <div class="row page-titles mx-0">
                    <div class="col-sm-6 p-md-0">
                        <div class="welcome-text">
                        <a href="#"><h4>Edit Course</h4></a>
                        </div>
                    </div>
                    <div class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="all-courses">Home</a></li>
                            <li class="breadcrumb-item active"><a href="javascript:void(0);">Courses</a></li>
                            <li class="breadcrumb-item active"><a href="#">Edit Course</a></li>
                        </ol>
                    </div>
                </div>
				
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">Courses Details</h4>
							</div>
							<div class="card-body">
								<form action="updatecourses" method="post" enctype="multipart/form-data">
									<div class="row">
										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="form-group">
												<label class="form-label">Course Title</label>
												<input type="text" class="form-control" name="coursetitle" value="${c.title}">
											</div>
										</div>
								<input type="hidden" id="hiddenInput" name="courseid" value="${c.id}"/>
										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="form-group">
												<label class="form-label">Course Code</label>
												<input type="text" class="form-control" name="coursecode" value="${c.code}">
											</div>
										</div>
										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="form-group">
												<label class="form-label">Department</label>
												<select class="form-control" name="coursedept" id="coursedept">
													<option value="${c.department}" selected>${c.department}</option>
													<option value="Computer Science & Engineering">Computer Science & Engineering</option>
													<option value="Electronics & Communication Engineering">Electronics & Communication Engineering</option>
													<option value="Civil Engineering">Civil Engineering</option>
													<option value="Bio Technology">Bio Technology</option>
													<option value="Information Technology">Information Technology</option>
													<option value="Mechanical Engineering">Mechanical Engineering</option>
													<option value="Electrical & Electronics Engineering">Electrical & Electronics Engineering </option>
													<option value="Electronics & Computer Engineering">Electronics & Computer Engineering </option>
												</select>
											</div>
										</div>
										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="form-group">
												<label class="form-label">Professor</label>
												<select class="form-control" name="professor" id="professor">
													<option value="${c.professor}" selected>${c.professor}</option>
													<c:forEach items="${professorS}" var="p">
                                                        <option value="${p.id}" >${p.firstname} ${p.lastname} - ${p.id}</option>
                                                    </c:forEach>
												</select>
											</div>
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12">
											<div class="form-group">
												<label class="form-label">Course Details</label>
												<textarea class="form-control" rows="5" name="coursedetails" >${c.description}</textarea>
											</div>
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12">
											<div class="form-group">
    											<label class="form-label">Course Image</label>
    											<div class="image-container">
      												<img src='displaycourseimage?id=${c.id}' alt="Course Image" height="250px" width="300px"> 
    											</div>
    											<input type="file" class="form-control" data-default-file="${c.cimage}" name="courseimage" accept=".jpg, .jpeg, .png" style="border: 1px solid #ced4da; " required="required">
        									<p style="color: #6c757d; font-size: 14px;">Maximum size should be 10MB</p>
  											</div>
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12">
											<button type="submit" class="btn btn-primary">Submit</button>
											<button type="submit" class="btn btn-light" onclick="window.location.href = 'all-courses';">Cancel</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				
            </div>
        </div>
        <!--**********************************
            Content body end
        ***********************************-->


        <!--**********************************
            Footer start
        ***********************************-->
        <div class="footer">
            <div class="copyright">
                <p>Copyright © Designed &amp; Developed by <a href="../index.jsp" target="_blank">AcademeX</a> 2023</p>
            </div>
        </div>
        <!--**********************************
            Footer end
        ***********************************-->

		

    </div>
    <!--**********************************
        Main wrapper end
    ***********************************-->

    <!--**********************************
        Scripts
    ***********************************-->
    <!-- Required vendors -->
<script src="vendor/global/global.min.js"></script>
	<script src="vendor/bootstrap-select/dist/js/bootstrap-select.min.js"></script>
    <script src="js/custom.min.js"></script>
	<script src="js/dlabnav-init.js"></script>

    <!-- Demo scripts -->
    <script src="js/dashboard/dashboard-2.js"></script>

	<!-- Svganimation scripts -->
    <script src="vendor/svganimation/vivus.min.js"></script>
    <script src="vendor/svganimation/svg.animation.js"></script>
    <script src="js/styleSwitcher.js"></script>
	
	<!-- pickdate -->
    <script src="vendor/pickadate/picker.js"></script>
    <script src="vendor/pickadate/picker.time.js"></script>
    <script src="vendor/pickadate/picker.date.js"></script>
	
	<!-- Pickdate -->
    <script src="js/plugins-init/pickadate-init.js"></script>
	<script>
    document.getElementById('coursedetails').value = ${p.description};
    </script>
</body>
</html>