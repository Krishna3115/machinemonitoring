import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./TechnicianPanel.css";
import React from "react";
import Sidebar from "./SidebarTech";

export default function TechnicianDashboard() {
  const navigate = useNavigate();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const name = localStorage.getItem("name");
  const profilePhotoUrl = localStorage.getItem("profilePhotoUrl") || "/default-avatar.png";

  return (
    <div className="technician-dashboard">
      <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />
      <nav className="technician-navbar">
        <button className="hamburger-btn" onClick={() => setSidebarOpen(true)}>
          ☰
        </button>
        <div className="logo">TBL Monitoring</div>
      </nav>

      <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} navigate={navigate} />

      <div className="technician-content">
        <h2>👷 Welcome, {name ? name : "Technician"}</h2>
        <div className="technician-actions">
          <div className="action-card" onClick={() => navigate("/technician/assign-tasks")}>
            📦 Pending Tasks
          </div>
          <div className="action-card" onClick={() => navigate("/technician/start-installation")}>
            🛠 Installation
          </div>
          <div className="action-card" onClick={() => navigate("/technician/start-maintenance")}>
            🧰 Maintenance
          </div>
          <div className="action-card" onClick={() => navigate("/technician/add-vandalism")}>
            🚨 Report Vandalism
          </div>
          <div className="action-card" onClick={() => navigate("/technician/grease-refill")}>
            🛢️ Grease Refill
          </div>
          <div className="action-card" onClick={() => navigate("/technician/replaced-parts")}>
            🔁 Replace Parts
          </div>
          <div className="action-card" onClick={() => navigate("/technician/helpdesk")}>
            ☎️ Help Desk
          </div>
        </div>
      </div>
    </div>
  );
}
