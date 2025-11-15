import React, { useState, useEffect } from 'react';
import axios from 'axios';
import API_BASE_URL from '../apiConfig';
import './NotificationPreference.css';

export default function NotificationPreferences() {
  const token = localStorage.getItem('token');
  const [prefs, setPrefs] = useState({ email: false, system: false });

  useEffect(() => {
    axios.get(`${API_BASE_URL}/api/admin/notifications`, { headers: { Authorization: `Bearer ${token}` } })
      .then(res => setPrefs(res.data))
      .catch(console.error);
  }, [token]);

  const save = () => {
    axios.put(`${API_BASE_URL}/api/admin/notifications`, prefs, { headers: { Authorization: `Bearer ${token}` } })
      .then(() => alert('Preferences saved!'))
      .catch(err => alert(err.response.data?.message || 'Save failed'));
  };

  return (
    <div className="settings-page">
      <h2>Notification Preferences</h2>
      <label><input type="checkbox" checked={prefs.email} onChange={e => setPrefs({ ...prefs, email: e.target.checked })} /> Email Notifications</label>
      <label><input type="checkbox" checked={prefs.system} onChange={e => setPrefs({ ...prefs, system: e.target.checked })} /> System Alerts</label>
      <button onClick={save}>Save Preferences</button>
    </div>
  );
}
