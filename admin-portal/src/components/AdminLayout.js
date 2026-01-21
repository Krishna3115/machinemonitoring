import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import ReminderPopup from './ReminderPopup'; // ✅ Make sure this path is correct
import './AdminLayout.css';

export default function AdminLayout({ children }) {
  const navigate = useNavigate();
  const [adminName, setAdminName] = useState('Admin');

  const [showPO, setShowPO] = useState(false);
  const [showProduction, setShowProduction] = useState(false);
  const [showDispatch, setShowDispatch] = useState(false);
  const [showVandalism, setShowVandalism] = useState(false);
  const [showReports, setShowReports] = useState(false);
  const [showSettings, setShowSettings] = useState(false);

  const [sidebarOpen, setSidebarOpen] = useState(false);


const [pendingActivationCount, setPendingActivationCount] = useState(0); // optional if you want badges


  useEffect(() => {
    const token = localStorage.getItem('token');

    if (token) {
      axios
        .get('/api/admin/me', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((res) => {
          const name = res.data.name || 'Admin';
          setAdminName(name);
          localStorage.setItem('adminName', name); // optional
        })
        .catch((err) => {
          console.error('Error fetching admin info:', err);
          setAdminName('Admin');
        });
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <div className={`admin-layout ${sidebarOpen ? "sidebar-open" : "sidebar-closed"}`}>

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
            <li onClick={() => navigate('/admin')}>Dashboard</li>
            
            {/* Purchase Orders */}
            <li className="dropdown-toggle" onClick={() => setShowPO(prev => !prev)}>
              Sales <span className="dropdown-icon">{showPO ? "▲" : "▼"}</span>
              {showPO && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/purchase-ordered')}>Sales Order</li>
                  <li onClick={() => navigate('/admin/edit-purchase-order')}>Edit Sales Order</li>
                </ul>
              )}
            </li>

            {/* Production */}
            <li className="dropdown-toggle" onClick={() => setShowProduction(prev => !prev)}>
              Production <span className="dropdown-icon">{showProduction ? "▲" : "▼"}</span>
              {showProduction && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/production-plan')}>Production Planning</li>
                  <li onClick={() => navigate('/admin/add-job-card-details')}>Add New Batch Card</li>
                  <li onClick={() => navigate('/admin/add-production-form')}>Add Production Entry</li>
                </ul>
              )}
            </li>

            {/* Dispatch */}
            <li className="dropdown-toggle" onClick={() => setShowDispatch(prev => !prev)}>
              Dispatch <span className="dropdown-icon">{showDispatch ? "▲" : "▼"}</span>
              {showDispatch && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/add-dispatch')}>New Dispatch</li>
                  <li onClick={() => navigate('/admin/pending-deliveries')}>Pending Deliveries</li>
                </ul>
              )}
            </li>

            <li onClick={() => navigate('/admin/activation-code')}>
              Activation Code {pendingActivationCount > 0 && <span className="badge">{pendingActivationCount}</span>}
            </li>

            <li onClick={() => navigate('/admin/addNewTechnician')}>Add New Technician</li>
            <li onClick={() => navigate('/admin/assign-tech')}>Assign Technician</li>

            {/* Vandalism */}
            <li className="dropdown-toggle" onClick={() => setShowVandalism(prev => !prev)}>
              Vandalism <span className="dropdown-icon">{showVandalism ? "▲" : "▼"}</span>
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

            {/* Reports */}
            <li className="dropdown-toggle" onClick={() => setShowReports(prev => !prev)}>
              Report <span className="dropdown-icon">{showReports ? "▲" : "▼"}</span>
              {showReports && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/dispatch-report')}>Dispatch Report</li>
                  <li onClick={() => navigate('/admin/installation-report')}>Installation Report</li>
                  <li onClick={() => navigate('/admin/maintenance-report')}>Maintenance Report</li>
                  <li onClick={() => navigate('/admin/reports/complaints')}>Customer Complaint Report</li>
                  <li onClick={() => navigate('/admin/reports/active-machines')}>Total Active Machine Report</li>
                  <li onClick={() => navigate('/admin/reports/warranty')}>Machine Warranty Report</li>
                  <li onClick={() => navigate('/admin/report/master-report')}>Master Report</li>
                </ul>
              )}
            </li>

            {/* Settings */}
            <li className="dropdown-toggle" onClick={() => setShowSettings(prev => !prev)}>
              Settings <span className="dropdown-icon">{showSettings ? "▲" : "▼"}</span>
              {showSettings && (
                <ul className="dropdown">
                  <li onClick={() => navigate('/admin/profile-setting')}>Profile Settings</li>
                  <li onClick={() => navigate('/admin/change-password')}>Change Password</li>
                  <li onClick={() => navigate('/admin/notification-preference')}>Notification Preferences</li>
                  <li onClick={() => navigate('/admin/security-settings')}>Security Settings</li>
                </ul>
              )}
            </li>

            {/* Keep existing buttons */}
            <li onClick={() => navigate('/technicians')}>Technicians</li>
            <li onClick={() => navigate('/admin/blocked-technicians')}>Block Technicians</li>
            <li onClick={handleLogout}>Logout</li>
          </ul>
        </nav>
      </aside>

      <div className="main-content">
        <header className="topbar">
          {
            <button
                className="sidebar-toggle"
                onClick={() => setSidebarOpen(true)}
              >
                ☰
              </button>

          /* You can add topbar content here */}
        </header>

        {/* ✅ Reminder Popup (shows globally on all admin pages) */}
        <ReminderPopup />

        <div className="content">{children}</div>

        <footer className="footer">
          © 2025 Chakradhar | All rights reserved.
        </footer>
      </div>
    </div>
  );
}
