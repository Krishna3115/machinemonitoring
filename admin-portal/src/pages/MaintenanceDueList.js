import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import './MaintenanceDueList.css';
import API_BASE_URL from '../apiConfig';
import { useNavigate } from 'react-router-dom';

export default function MaintenanceDueList() {
  const [machines, setMachines] = useState([]);
  const [greaseExpiryData, setGreaseExpiryData] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchDueMachines();
    fetchGreaseExpiringSoon();
  }, []);

  const fetchGreaseExpiringSoon = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${API_BASE_URL}/api/grease/low-level`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setGreaseExpiryData(res.data || []);
    } catch (err) {
      console.error('Error fetching grease expiry:', err);
    }
  };

  const fetchDueMachines = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${API_BASE_URL}/api/inspections/upcoming`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMachines(res.data || []);
    } catch (err) {
      console.error('Error fetching due maintenance:', err);
    }
  };

  // Convert API due_date array to JS Date object
  const parseDueDate = (dueDateArr) => {
    if (!Array.isArray(dueDateArr) || dueDateArr.length < 3) return null;
    const [year, month, day, hour = 0, minute = 0, second = 0] = dueDateArr;
    return new Date(year, month - 1, day, hour, minute, second);
  };

  const getDaysStatus = (dueDateArr) => {
    const dueDate = parseDueDate(dueDateArr);
    if (!dueDate || isNaN(dueDate)) return '–';
    const today = new Date();
    const daysDiff = Math.ceil((dueDate - today) / (1000 * 60 * 60 * 24));

    if (daysDiff < 0) return `Overdue by ${Math.abs(daysDiff)} day(s)`;
    if (daysDiff === 0) return 'Due Today';
    return `In ${daysDiff} day(s)`;
  };

  const getStatusClassByDays = (dueDateArr) => {
    const dueDate = parseDueDate(dueDateArr);
    if (!dueDate || isNaN(dueDate)) return '';
    const daysDiff = Math.ceil((dueDate - new Date()) / (1000 * 60 * 60 * 24));

    if (daysDiff < 0) return 'status-blocked'; // Overdue
    if (daysDiff <= 6) return 'status-red';
    if (daysDiff <= 10) return 'status-orange';
    if (daysDiff <= 15) return 'status-warning';
    return 'status-active';
  };

  const filteredMachines = machines.filter((machine) => {
    const daysStatus = getDaysStatus(machine.due_date).toLowerCase();
    const modelNo = (machine.model_no || '').toLowerCase();
    const dueDate = parseDueDate(machine.due_date)
      ? parseDueDate(machine.due_date).toLocaleDateString().toLowerCase()
      : '';
    const term = searchTerm.toLowerCase();
    return daysStatus.includes(term) || modelNo.includes(term) || dueDate.includes(term);
  });

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Machine Maintenance Status</h2>

        <div className="status-legend">
          <span><span className="legend-box yellow"></span> 15 days (Warning)</span>
          <span><span className="legend-box orange"></span> 10 days (Attention)</span>
          <span><span className="legend-box red"></span> 6 days (Critical)</span>
          <span><span className="legend-box blocked"></span> Overdue</span>
        </div>

        <input
          type="text"
          placeholder="Search by model, days, or due date"
          className="search-input"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />

        <table className="technician-table">
          <thead>
            <tr>
              <th>Machine ID</th>
              <th>Model No</th>
              <th>Due Date</th>
              <th>Status</th>
              <th>Days Remaining</th>
              <th>Grease Empty In</th>
              <th>Assign Technician</th>
            </tr>
          </thead>
          <tbody>
            {filteredMachines.length === 0 ? (
              <tr>
                <td colSpan="7" style={{ textAlign: 'center' }}>
                  No machines found.
                </td>
              </tr>
            ) : (
              filteredMachines.map((machine) => {
                const dueDateObj = parseDueDate(machine.due_date);
                const greaseInfo = greaseExpiryData.find(g => g.model_no === machine.model_no);

                return (
                  <tr key={machine.machine_id || Math.random()}>
                    <td>{machine.machine_id || '–'}</td>
                    <td>{machine.model_no || '–'}</td>
                    <td>{dueDateObj ? dueDateObj.toLocaleDateString() : '–'}</td>
                    <td className={getStatusClassByDays(machine.due_date)}>
                      {machine.status || '–'}
                    </td>
                    <td>{getDaysStatus(machine.due_date)}</td>
                    <td>
                      {greaseInfo && greaseInfo.days_until_empty !== undefined && greaseInfo.days_until_empty <= 10
                        ? `In ${greaseInfo.days_until_empty} day(s)`
                        : 'OK'}
                    </td>
                    <td>
                      <button
                        className="action-button"
                        onClick={() =>
                          navigate('/admin/assign-tech', {
                            state: { modelNo: machine.model_no }
                          })
                        }
                      >
                        Assign Technician
                      </button>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>
    </AdminLayout>
  );
}
