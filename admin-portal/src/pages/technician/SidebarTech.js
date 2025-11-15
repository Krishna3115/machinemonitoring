// SidebarTech.js
import React from "react";
import "./SidebarTech.css";

const SidebarTech = ({ isOpen, onClose }) => {
  // ✅ Use the correct localStorage key: "userId"
  const userId = localStorage.getItem("userId");  
  const profilePhotoUrl = localStorage.getItem("profilePhotoUrl") || "/default-avatar.png"; // fallback if not available
  const name = localStorage.getItem("name");

  return (
    <div className={`technician-sidebar ${isOpen ? "open" : ""}`}>
      <button className="technician-close-btn" onClick={onClose}>×</button>

      <div className="technician-profile">
        <img src={profilePhotoUrl} alt="Profile" className="technician-avatar" />
        <div className="technician-info">
          {/* ✅ Now it will show properly */}
          <div className="technician-id">ID: {userId || "N/A"}</div>
          <div className="technician-name">Name: {name || "Unknown"}</div>
        </div>
      </div>

      <ul className="technician-sidebar-menu">
        <li><a href="/technician/dashboard">🏠 Dashboard</a></li>
        <li><a href="/technician/profile">👤 My Profile</a></li>
        <li><a href="/technician/installations">🛠️ Installation Process</a></li>
        <li><a href="/technician/maintenance">🔧 Maintenance Process</a></li>
        <li><a href="/technician/guide">📘 Quick Guide</a></li>
        <li><a href="/technician/machines">📟 Machines</a></li>
        <li><a href="/technician/notifications">🔔 Notifications</a></li>
        <li><a href="/technician/emergency-support">🆘 Support</a></li>
        <li><a href="/logout">🚪 Logout</a></li>
      </ul>
    </div>
  );
};

export default SidebarTech;
