import "./TechnicianList.css";
import React, { useEffect, useState } from "react";
import AdminLayout from "../components/AdminLayout";
import axios from "axios";
import API_BASE_URL from "../apiConfig";

export default function TechnicianList() {
  const [technicians, setTechnicians] = useState([]);

  useEffect(() => {
    fetchTechnicians();
  }, []);

  const fetchTechnicians = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(
        `${API_BASE_URL}/api/users/technicians`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setTechnicians(response.data);
    } catch (error) {
      console.error("Error fetching technicians:", error);
    }
  };

  const block = async (id) => {
    try {
      const token = localStorage.getItem("token");
      await axios.post(
        `${API_BASE_URL}/api/users/technician/${id}/block`,
        null,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchTechnicians();
    } catch (error) {
      console.error("Error blocking technician:", error);
    }
  };

  const unblock = async (id) => {
    try {
      const token = localStorage.getItem("token");
      await axios.post(
        `${API_BASE_URL}/api/users/technician/${id}/unblock`,
        null,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchTechnicians();
    } catch (error) {
      console.error("Error unblocking technician:", error);
    }
  };

  return (
    <AdminLayout>
      <div className="technician-wrapper">
        <div className="technician-list-container">
          <h2>Technician List</h2>

          <div className="table-scroll">
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
                {technicians.map((tech) => (
                  <tr key={tech.id}>
                    <td>{tech.name}</td>
                    <td>{tech.mobile_number}</td>
                    <td>{tech.email}</td>
                    <td
                      className={
                        tech.isBlocked
                          ? "status-blocked"
                          : "status-active"
                      }
                    >
                      {tech.isBlocked ? "Blocked" : "Active"}
                    </td>
                    <td>
                      {tech.isBlocked ? (
                        <button
                          className="action-button unblock"
                          onClick={() => unblock(tech.id)}
                        >
                          Unblock
                        </button>
                      ) : (
                        <button
                          className="action-button block"
                          onClick={() => block(tech.id)}
                        >
                          Block
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </AdminLayout>
  );
}
