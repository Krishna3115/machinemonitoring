import React, { useState } from 'react';
import axios from 'axios';
import './AddNewTechnician.css'; // Updated CSS
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';

const AddTechnician = () => {
  const [formData, setFormData] = useState({
    name: '',
    mobileNumber: '',
    city: '',
    email: '',
    password: '',
    designation: 'installation'
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    try {
      await axios.post(`${API_BASE_URL}/api/users/register`, formData, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('Technician registered successfully!');
      setFormData({
        name: '',
        mobileNumber: '',
        city: '',
        email: '',
        password: '',
        designation: 'installation'
      });
    } catch (err) {
      console.error('Error registering technician:', err);
      alert('Failed to register technician!');
    }
  };

  return (
    <AdminLayout>
      <div className="form-container">
        <h2 className="form-heading">Add New Technician</h2>
        <form className="dispatch-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="name">Name</label>
            <input id="name" type="text" name="name" value={formData.name} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="mobileNumber">Mobile Number</label>
            <input id="mobileNumber" type="text" name="mobileNumber" placeholder="Enter 10-digit mobile number" maxLength={10} value={formData.mobileNumber} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="city">City</label>
            <input id="city" type="text" name="city" value={formData.city} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input id="email" type="email" name="email" value={formData.email} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input id="password" type="password" name="password" value={formData.password} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="designation">Designation</label>
            <select id="designation" name="designation" value={formData.designation} onChange={handleChange} required>
              <option value="installation">Installation</option>
              <option value="maintenance">Maintenance</option>
            </select>
          </div>

          <button type="submit" className="submit-btn">Register Technician</button>
        </form>
      </div>
    </AdminLayout>
  );
};

export default AddTechnician;
