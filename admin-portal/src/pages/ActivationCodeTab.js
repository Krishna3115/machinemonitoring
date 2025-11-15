import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import './ActivationCodeTab.css';  // your styling
import API_BASE_URL from "../apiConfig";

export default function ActivationCodeTab() {
  const [users, setUsers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [mobileNumber, setMobileNumber] = useState('');
  const [activationCode, setActivationCode] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchInactiveUsers();
  }, []);

  const fetchInactiveUsers = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`${API_BASE_URL}/api/users/inactive`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      // Map snake_case fields to camelCase for consistency
      const mappedUsers = response.data.map(user => ({
        ...user,
        mobileNumber: user.mobile_number,
        activationCode: user.activation_code
      }));

      setUsers(mappedUsers);
    } catch (error) {
      console.error("Error fetching inactive users:", error);
    }
  };

  const openModal = () => {
    setMobileNumber('');
    setActivationCode('');
    setShowModal(true);
  };

  const closeModal = () => setShowModal(false);

  const activateTechnician = async (e) => {
    e.preventDefault();
    if (!mobileNumber || !activationCode) {
      alert('Please enter both Mobile Number and Activation Code');
      return;
    }
    try {
      setLoading(true);
      const token = localStorage.getItem('token');
      await axios.post(`${API_BASE_URL}/api/users/activate`, 
        { mobileNumber, code: activationCode }, 
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert('Technician activated successfully!');
      closeModal();
      fetchInactiveUsers();
    } catch (error) {
      console.error('Error activating technician:', error);
      alert('Activation failed. Please check the details.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <AdminLayout>
      <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px'}}>
        <h2>Pending Activation Codes</h2>
        <button className="activate-button"
          onClick={openModal} 
          style={{ padding: '8px 16px', cursor: 'pointer' }}
        >
          Activate Technician
        </button>
      </div>

      <table className="activation-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Mobile Number</th>
            <th>Activation Code</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.mobileNumber}</td>
              <td>{user.activationCode}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h2>Activate Technician</h2>
            <form onSubmit={activateTechnician}>
              <label>
                Mobile Number:
                <input
                  type="text"
                  className="custom-input"
                  value={mobileNumber}
                  onChange={(e) => setMobileNumber(e.target.value)}
                  required
                />
              </label>
              <label>
                Activation Code:
                <input
                  type="text"
                  className="custom-input"
                  value={activationCode}
                  onChange={(e) => setActivationCode(e.target.value)}
                  required
                />
              </label>
              <div className="modal-buttons">
                <button type="submit" disabled={loading}>
                  {loading ? 'Activating...' : 'Activate'}
                </button>
                <button type="button" onClick={closeModal} disabled={loading}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </AdminLayout>
  );
}
