import React, { useState } from 'react';
import axios from 'axios';
import './AddNewTechnician.css'; // Reuse same styles
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';

const AddTechnician = () => {
  const [formData, setFormData] = useState({
    name: '',
    mobileNumber: '',
    city: '',
    email: '',
    password: ''
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

      // Reset form
      setFormData({
        name: '',
        mobile: '',
        email: '',
        password: ''
      });
    } catch (err) {
      console.error('Error registering technician:', err);
      alert('Failed to register technician!');
    }
  };

  return (
    <AdminLayout>
      <h2>Add New Technician</h2>
      <form className="dispatch-form" onSubmit={handleSubmit}>
        {Object.entries(formData).map(([key, value]) => (
          <div key={key} className="form-group">
            <label htmlFor={key}>{key.charAt(0).toUpperCase() + key.slice(1)}</label>
            <input
              id={key}
              type={key === 'password' ? 'password' : key === 'email' ? 'email' : 'text'}
              name={key}
              value={value}
              onChange={handleChange}
              required
            />
          </div>
        ))}
        <button type="submit" className="submit-btn">Register Technician</button>
      </form>
    </AdminLayout>
  );
};

export default AddTechnician;
