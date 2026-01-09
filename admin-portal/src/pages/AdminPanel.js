import "./AdminPanel.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import API_BASE_URL from "../apiConfig";
import backgroundVideo from "../assets/train_timelapse1.mp4";

import { FaBell, FaUserCircle, FaEnvelope } from "react-icons/fa";


export default function AdminPanel() {
  // Dummy data - replace with real API data later
  const [adminName, setAdminName] = useState("");
  const [userCount, setUserCount] = useState(0);
  //const [machineCount, setMachineCount] = useState(0); // Placeholder
 //const [departmentCount, setDepartmentCount] = useState(0); // Placeholder
  //const [projectCount, setProjectCount] = useState(0); // Placeholder
  const [pendingActivationCount, setPendingActivationCount] = useState(0);
  const [pendingforInstallation, setPendingInstallationCount] = useState(0);
  const [installationinprog, setInstallationInprogressCount] = useState(0);
  const [maintenanceDueCount, setMaintenanceDueCount] = useState(0);
  const [siteInspectionPendingCount, setSiteInspectionPendingCount] = useState(0);
  const [activeMachineCount, setActiveMachineCount] = useState(0);
  const [activeCustomerCount, setActiveCustomerComplaintCount] = useState(0);
  const [availableMachinesToDispatch, setAvailableMachinesToDispatch] = useState(0);
  const [jobOrderInprogress, setJobOrderInprogress] = useState(0);
  const [availableMachinesForQuality, setAvailableMachinesForQuality] = useState(0);
  const [machineUndermaintenance, setMachineUndermaintenance] = useState(0);


  const [showReports, setShowReports] = useState(false);
  //const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [showSettings, setShowSettings] = useState(false);


 // const [notificationCount, setNotificationCount] = useState(3);
 // const [messageCount, setMessageCount] = useState(5);

  const [showProduction, setShowProduction] = useState(false);
  const [showVandalism, setShowVandalism] = useState(false);
  const [showDispatch, setShowDispatch] = useState(false);

  const [showPO, setShowPO] = useState(false);


  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const toggleSidebar = () => {
  setSidebarOpen(prev => !prev);
};

  useEffect(() => {

    const storedName = localStorage.getItem("name");
    if (storedName) {
      setAdminName(storedName);
    }

    const fetchCounts = async () => {
      try {
        const token = localStorage.getItem("token");

        const userRes = await axios.get(`${API_BASE_URL}/api/users/count/users`, {
          headers: { Authorization: `Bearer ${token}` }
        });

        const pendingRes = await axios.get(`${API_BASE_URL}/api/users/inactive`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const deliveredRes = await axios.get(`${API_BASE_URL}/api/machines/status/DELIVERED`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const installationinprogRes = await axios.get(`${API_BASE_URL}/api/machines/status/INSTALLING`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const maintenanceRes = await axios.get(`${API_BASE_URL}/api/inspections/upcoming`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const siteInspectionRes = await axios.get(`${API_BASE_URL}/api/machines/site-inspection/pending`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const activeMachineCount = await axios.get(`${API_BASE_URL}/api/machines/status/COMPLETE`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const activeCustomerCount = await axios.get(`${API_BASE_URL}/api/complaints/pending/count`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const availableMachinesToDispatch = await axios.get(`${API_BASE_URL}/api/machines-production/count/ready-to-dispatch`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const jobOrderInprogress = await axios.get(`${API_BASE_URL}/api/job-cards/in-progress/count`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const availableMachinesForQuality = await axios.get(`${API_BASE_URL}/api/machines-production/available/count`, {
        headers: { Authorization: `Bearer ${token}` }
        });

        const machineUndermaintenance = await axios.get(`${API_BASE_URL}/api/machines/status/machine-status/under-maintenance-count`, {
        headers: { Authorization: `Bearer ${token}` }
        });



        setUserCount(userRes.data);
        setPendingActivationCount(pendingRes.data.length);
        setPendingInstallationCount(deliveredRes.data.length);
        setInstallationInprogressCount(installationinprogRes.data.length);
        setMaintenanceDueCount(maintenanceRes.data.length);
        setSiteInspectionPendingCount(siteInspectionRes.data.length);
        setActiveMachineCount(activeMachineCount.data.length);
        setActiveCustomerComplaintCount(activeCustomerCount.data);
        setAvailableMachinesToDispatch(availableMachinesToDispatch.data);
        setJobOrderInprogress(jobOrderInprogress.data);
        setAvailableMachinesForQuality(availableMachinesForQuality.data);
        setMachineUndermaintenance(machineUndermaintenance.data);
        

        // TODO: Add machine, department, project APIs here later
        // Example:
        // const machineRes = await axios.get("http://localhost:8080/api/machines/count");
        // setMachineCount(machineRes.data);

      } catch (err) {
        console.error("Failed to fetch counts:", err);
      }
    };

    fetchCounts();
  }, []);


  const stats = [

    { label: "Production Status", count: jobOrderInprogress },
    { label: "Machine Pending For Quality Check", count: availableMachinesForQuality },
    { label: "Available Machines For Dispatch", count: availableMachinesToDispatch },
    { label: "Machine Pending for Installation", count: pendingforInstallation },
    { label: "Installation in Process", count: installationinprog },
    { label: "Site Final Inspection Pending", count: siteInspectionPendingCount },
    {
    label: "Total Machines",
    count: activeMachineCount + machineUndermaintenance,
    subStats: [
      { subLabel: "Active Machines", value: activeMachineCount },
      { subLabel: "Under Maintenance", value: machineUndermaintenance }
    ]
  },

    { label: "Maintenance In 10 days", count: maintenanceDueCount },
    { label: "Active Customer Complaint", count: activeCustomerCount },
    { label: "Vandalised Machines", count: machineUndermaintenance },
    { label: "Active Technicians", count: userCount },
    { label: "Assigned Technician", count: activeCustomerCount },
    
       
   
  ];


  return (
    <div className={`admin-panel ${sidebarOpen ? "sidebar-open" : "sidebar-closed"}`}>

      <aside className="sidebar">

         <button
            className="sidebar-close"
            onClick={() => setSidebarOpen(false)}
          >
            ✕
          </button>

        <h1>Admin Panel</h1>
        <nav>
          <ul>
            <li onClick={() => setShowPO(!showPO)}>
                  P.O
                  <span className="dropdown-icon">{showPO ? "▲" : "▼"}</span>
                  {showPO && (
                    <ul className="dropdown">
                      <li onClick={() => navigate('/admin/purchase-ordered')}>P.O Sales Ordered</li>
                      <li onClick={() => navigate('/admin/edit-purchase-order')}>Edit P.O</li>
                    </ul>
                  )}
                </li>
                
            <li onClick={() => setShowProduction(!showProduction)}>
                Production
                <span className="dropdown-icon">{showProduction ? "▲" : "▼"}</span>
                {showProduction && (
                  <ul className="dropdown">
                    
                    <li onClick={() => navigate('/admin/production-plan')}>Production Planning</li>
                    <li onClick={() => navigate('/admin/add-job-card-details')}>Add New Batch Card</li>
                    <li onClick={() => navigate('/admin/add-production-form')}>Add Production Entry</li>
                  </ul>
                )}
              </li>


            <li onClick={() => setShowDispatch(!showDispatch)}>
              Dispatch
              <span className="dropdown-icon">{showDispatch ? "▲" : "▼"}</span>
              {showDispatch && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/add-dispatch')}>New Dispatch</li>
                  <li onClick={() => navigate('/admin/pending-deliveries')}>Pending Deliveries</li>
                </ul>
              )}
            </li>
 
            <li onClick={() => navigate('/admin/activation-code')}>
              Activation Code
              {pendingActivationCount > 0 && <span className="badge">{pendingActivationCount}</span>}
            </li>
            <li onClick={() => navigate('/admin/addNewTechnician')}>Add New Technician</li>
            <li onClick={() => navigate('/admin/assign-tech')}>Assign Technician</li>
            
            <li onClick={() => setShowVandalism(!showVandalism)}>
                  Vandalism
                  <span className="dropdown-icon">{showVandalism ? "▲" : "▼"}</span>
                  {showVandalism && (
                    <ul className="dropdown">
                      <li onClick={() => navigate('/admin/vandalism-report')}>Vandalism Report</li>
                      <li onClick={() => navigate('/admin/insurance/process-list')}>Claim Process</li>
                    </ul>
                  )}
                </li>

            <li onClick={() => navigate('/admin/customer_complaint')}>Customer Complaints</li>

            <li onClick={() => navigate('/admin/frequncy-form')}>Add Frequency</li>
            <li onClick={() => navigate('/admin/contact-details')}>Contact Details</li>

            <li onClick={() => navigate('/admin/vandalism/part-replacement')}>Parts Replacement</li>
          
            {/* Report Dropdown */}
            <li onClick={() => setShowReports(!showReports)}>
              Report
              <span className="dropdown-icon">{showReports ? "▲" : "▼"}</span>
              {showReports && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/dispatch-report')}>Dispatch Report</li>
                  <li onClick={() => navigate('/admin/installation-report ')}>Installation Report</li>
                  <li onClick={() => navigate('/admin/maintenance-report')}>Maintenance Report</li>
                  <li onClick={() => navigate('/admin/reports/complaints')}>Customer Complaint Report</li>
                  <li onClick={() => navigate('/admin/reports/active-machines')}>Total Active Machine Report</li>
                  <li onClick={() => navigate('/admin/reports/warranty')}>Machine Warranty Report</li>
                  <li onClick={() => navigate('/admin/report/master-report')}>Master Report</li>
                </ul>
              )}
            </li>

            <li onClick={() => setShowSettings(prev => !prev)}>
                  Settings
                  <span className="dropdown-icon">{showSettings ? "▲" : "▼"}</span>
                  {showSettings && (
                    <ul className="dropdown">
                      <li onClick={() => navigate('/admin/profile-setting')}>Profile Settings</li>
                      <li onClick={() => navigate('/admin/change-password')}>Change Password</li>
                      <li onClick={() => navigate('/admin/notification-preference')}>Notification Preferences</li>
                      <li onClick={() => navigate('/admin/security-settings')}>Security Settings</li>
                    </ul>
                  )}
            </li>

            <li onClick={handleLogout}>Logout</li>
          </ul>
        </nav> 
      </aside>

      <main className="dashboard-content">
  <div className="video-container">
    <video
      className="dashboard-video"
      autoPlay
      muted
      loop
      playsInline
    >
      <source src={backgroundVideo} type="video/mp4" />
      Your browser does not support the video tag.
    </video>
  </div>

  <div className="dashboard-overlay">
    <header className="dashboard-header">
      <div className="header-row">
       <button className="sidebar-toggle" onClick={toggleSidebar}>⋮</button>
      <h3>Hello, {adminName}</h3>
       {/* ======= New right side icons container ======= */}
              <div className="header-right-icons">
                <div
                  className="icon-wrapper"
                  onClick={() => navigate("/admin/notifications")}
                  title="Notifications"
                >
                  <FaBell />
                  {notificationCount > 0 && <span className="badge">{notificationCount}</span>}
                </div>

                <div
                  className="icon-wrapper"
                  onClick={() => navigate("/admin/messages")}
                  title="Messages"
                >
                  <FaEnvelope />
                  {messageCount > 0 && <span className="badge">{messageCount}</span>}
                </div>

                <div
                  className="icon-wrapper"
                  onClick={() => navigate("/admin/profile-setting")}
                  title="Profile"
                >
                  <FaUserCircle />
                </div>
              </div>
                       
      </div>


      <h1>Welcome To Dashboard</h1>
    </header>

    <div className="scrollable-stats">
      <div className="stats-grid">
        {stats.map((item) => (
  <div
    key={item.label}
    className={`stat-box ${item.subStats ? "has-substats" : ""}`}
    onClick={() => {
      if (item.label === "Production Status") navigate("/admin/job-order-progress");
      if (item.label === "Machine Pending For Quality Check") navigate("/admin/pending-quality-check");
      if (item.label === "Available Machines For Dispatch") navigate("/admin/available-machines-dispatch");
      if (item.label === "Active Technicians") navigate("/admin/technicians");
      if (item.label === "Machine Pending for Installation") navigate("/admin/delivered-machines");
      if (item.label === "Maintenance In 10 days") navigate("/admin/maintenance_dueList");
      if (item.label === "Site Final Inspection Pending") navigate("/admin/site-inspections");
      if (item.label === "Installation in Process") navigate("/admin/installations-in-progress");
      if (item.label === "Total Machines") navigate("/admin/active-machines");
      if (item.label === "Active Customer Complaint") navigate("/admin/complaint-count");
      if (item.label === "Vandalised Machines") navigate("/admin/vandalism-report");
    }}
  >
    <h3>{item.count}</h3>
    <p>{item.label}</p>

    {/* Render substats if available */}
    
    
    
    {item.subStats && (
  <div className="substats">
    {item.subStats.map((sub, idx) => (
      <div
        key={idx}
        className="substat-item clickable"
        onClick={(e) => {
          e.stopPropagation(); // Prevent main box click
          
          if (sub.subLabel === "Under Maintenance") {
            navigate("/admin/machine-undermaintenance");
          }

          if (sub.subLabel === "Active Machines") {
            navigate("/admin/active-machines");
          }
        }}
      >
        <span>{sub.subLabel}</span>
        <span className="substat-count">{sub.value}</span>
      </div>
    ))}
  </div>
)}
  </div>
))}      </div>
    </div>
  </div>
</main>

    </div>
  );
}