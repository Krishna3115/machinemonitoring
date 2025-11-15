import React from 'react';
import axios from 'axios';
import API_BASE_URL from '../apiConfig';
import './SecuritySetting.css';

export default function SecuritySettings() {
  const token = localStorage.getItem('token');

  const logoutAll = () => {
    if (!window.confirm('Logout all sessions?')) return;
    axios.post(`${API_BASE_URL}/api/admin/logout-all`, {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(() => {
        localStorage.removeItem('token');
        window.location.reload();
      })
      .catch(err => alert(err.response.data?.message || 'Failed'));
  };

  return (
    <div className="settings-page">
      <h2>Security Settings</h2>
      <button className="danger-btn" onClick={logoutAll}>Logout All Devices</button>
      <p>This will log you out from all other sessions.</p>
    </div>
  );
}
