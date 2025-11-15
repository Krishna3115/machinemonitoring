import React, { useState } from 'react';
import axios from 'axios';
import API_BASE_URL from '../apiConfig';
import './ChangePassword.css';

export default function ChangePassword() {
  const token = localStorage.getItem('token');
  const [form, setForm] = useState({ current: '', new: '', confirm: '' });

  const handleSubmit = () => {
    if (form.new !== form.confirm) return alert("New passwords don't match");
    axios.post(`${API_BASE_URL}/api/admin/change-password`, form, { headers: { Authorization: `Bearer ${token}` } })
      .then(() => alert('Password changed!'))
      .catch(err => alert(err.response.data?.message || 'Change failed'));
  };

  return (
    <div className="settings-page">
      <h2>Change Password</h2>
      <label>Current Password</label>
      <input type="password" onChange={e => setForm({ ...form, current: e.target.value })} />
      <label>New Password</label>
      <input type="password" onChange={e => setForm({ ...form, new: e.target.value })} />
      <label>Confirm New Password</label>
      <input type="password" onChange={e => setForm({ ...form, confirm: e.target.value })} />
      <button onClick={handleSubmit}>Update Password</button>
    </div>
  );
}
