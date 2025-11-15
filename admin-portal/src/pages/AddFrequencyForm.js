// File: AddFrequencyForm.jsx

import React, { useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import "./Customer_complaints.css"; // reusing styling

const AddFrequencyForm = () => {
  const [formData, setFormData] = useState({
    model_no: "",
    wheels_per_day: "",
    grease_release_rate: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };
  

 const handleSubmit = async (e) => {
  e.preventDefault();
  const token = localStorage.getItem("token");

  const payload = {
    modelNo: formData.model_no,
    wheelsPerDay: parseInt(formData.wheels_per_day),
    greaseReleaseRate: parseFloat(formData.grease_release_rate)
  };

  try {
    await axios.post(`${API_BASE_URL}/api/grease-frequency/add`, payload, {
      headers: {
        Authorization: `Bearer ${token}`,
        'username': localStorage.getItem('name') || 'admin', // Optional: track updater
        'Content-Type': 'application/json'
      }
    });

    alert("✅ Frequency data saved successfully!");
    setFormData({
      model_no: "",
      wheels_per_day: "",
      grease_release_rate: ""
    });
  } catch (err) {
    console.error("Error saving frequency:", err);
    alert("❌ Failed to save frequency.");
  }
};

  return (
    <AdminLayout>
      <h2>📊 Add Grease Frequency</h2>

      <form className="dispatch-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="model_no">Machine Serial No</label>
          <input
            type="text"
            id="model_no"
            name="model_no"
            value={formData.model_no}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="wheels_per_day">Wheels Per Day</label>
          <input
            type="number"
            id="wheels_per_day"
            name="wheels_per_day"
            value={formData.wheels_per_day}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="grease_release_rate">Grease Release Rate (g/sec)</label>
          <input
            type="number"
            id="grease_release_rate"
            name="grease_release_rate"
            value={formData.grease_release_rate}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" className="submit-btn">💾 Save Frequency</button>
      </form>
    </AdminLayout>
  );
};

export default AddFrequencyForm;
