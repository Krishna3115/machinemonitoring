// DeliveredMachines.jsx
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import './DeliveredMachines.css';
import API_BASE_URL from '../apiConfig';
import { useNavigate } from 'react-router-dom';

export default function DeliveredMachines() {
  const [machines, setMachines] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDeliveredMachines = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await axios.get(`${API_BASE_URL}/api/machines/status/DELIVERED`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setMachines(res.data);
      } catch (err) {
        console.error("Failed to fetch delivered machines", err);
      }
    };

    fetchDeliveredMachines();
  }, []);

  const parseBackendDate = (dateArray) => {
    if (!dateArray || !Array.isArray(dateArray)) return null;
    const [year, month, day, hour, minute] = dateArray;
    return new Date(year, month - 1, day, hour, minute);
  };

  const formatDate = (dateArray) => {
    const date = parseBackendDate(dateArray);
    if (!date) return "N/A";
    return `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()}`;
  };

  const calculateDaysSinceDelivery = (array) => {
    const delivered = parseBackendDate(array);
    if (!delivered) return "-";
    const now = new Date();
    return Math.floor((now - delivered) / (1000 * 60 * 60 * 24));
  };

  return (
    <AdminLayout>
      <div className="delivered-machines-container">
        <h2>Machines Pending for Installation</h2>

        <table className="delivered-table">
          <thead>
            <tr>
              <th>Sr. No</th>
              <th>Machine Serial No</th>
              <th>Machine Name</th>
              <th>Delivered Location</th>
              <th>Delivered Date</th>
              <th>Days Since Delivery</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {machines.length === 0 ? (
              <tr>
                <td colSpan="7" style={{ textAlign: "center" }}>
                  No delivered machines found
                </td>
              </tr>
            ) : (
              machines.map((machine, index) => (
                <tr key={machine.id}>
                  <td>{index + 1}</td>
                  <td>{machine.model_no}</td>
                  <td>{machine.machine_name}</td>
                  <td>{machine.division}</td>
                  <td>{formatDate(machine.delivered_date)}</td>
                  <td>{calculateDaysSinceDelivery(machine.delivered_date)} days</td>

                  <td>
                    <button
                      className="action-button"
                      onClick={() =>
                        navigate('/admin/assign-tech', {
                          state: { modelNo: machine.model_no } // send model number
                        })
                      }
                    >
                      Assign Technician
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </AdminLayout>
  );
}
