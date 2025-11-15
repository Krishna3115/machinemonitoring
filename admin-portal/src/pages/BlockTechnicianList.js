import "./BlockTechnicianList.css";
import React, { useEffect, useState } from 'react';
import AdminLayout from '../components/AdminLayout';
import axios from 'axios';
import API_BASE_URL from "../apiConfig";

export default function BlockedTechnicianList() {
  const [blockedTechnicians, setBlockedTechnicians] = useState([]);

  useEffect(() => {
    fetchBlockedTechnicians();
  }, []);

  const fetchBlockedTechnicians = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`${API_BASE_URL}/api/users/blocked-technicians`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setBlockedTechnicians(response.data);
    } catch (error) {
      console.error("Error fetching blocked technicians:", error);
    }
  };

  const unblockTechnician = async (id) => {
    try {
      const token = localStorage.getItem('token');
      await axios.post(`${API_BASE_URL}/api/users/technician/${id}/unblock`, null, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchBlockedTechnicians();  // Refresh the list
    } catch (error) {
      console.error("Error unblocking technician:", error);
    }
  };

  return (
    <AdminLayout>
      <h2>Blocked Technicians</h2>
      <table className="technician-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Mobile</th>
            <th>Email</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {blockedTechnicians.length === 0 ? (
            <tr><td colSpan="5">No blocked technicians</td></tr>
          ) : (
            blockedTechnicians.map((tech) => (
              <tr key={tech.id}>
                <td>{tech.name}</td>
                <td>{tech.mobileNumber}</td>
                <td>{tech.email}</td>
                <td className="status-blocked">Blocked</td>
                <td>
                  <button
                    className="action-button unblock"
                    onClick={() => unblockTechnician(tech.id)}
                  >
                    Unblock
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </AdminLayout>
  );
}
