import React, { useState } from 'react';
import axios from 'axios';
import './Customer_complaints.css';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';

const AddComplaintForm = () => {
  const [formData, setFormData] = useState({
    division: '',
    section: '',
    from_km: '',
    to_km: '',
    model_no: '',
    machine_issue: '',
    file: null // raw file here
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleFileChange = (e) => {
    setFormData(prev => ({
      ...prev,
      file: e.target.files[0]
    }));
  };

  const fetchModelNo = async () => {
    try {
      const { division, section, from_km, to_km } = formData;

      const res = await axios.get(`${API_BASE_URL}/api/machines/find-model`, {
        params: {
          division,
          section,
          kmFrom: from_km,
          kmTo: to_km
        }
      });

      if (res.data && res.data.length > 0) {
        setFormData(prev => ({ ...prev, model_no: res.data[0] }));
      } else {
        alert('Model number not found for provided info.');
      }
    } catch (error) {
      console.error('Error fetching model number:', error);
      alert('Failed to fetch model number.');
    }
  };

  const handleFileUpload = async (file) => {
    const data = new FormData();
    data.append('file', file);

    try {
      const res = await axios.post(`${API_BASE_URL}/api/upload/image`, data, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      return res.data.url;
    } catch (err) {
      console.error('File upload failed:', err);
      alert('File upload failed!');
      return null;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    // Upload file first
    let uploadedUrl = '';
    if (formData.file) {
      uploadedUrl = await handleFileUpload(formData.file);
      if (!uploadedUrl) return;
    }

    const payload = {
      division: formData.division,
      section: formData.section,
      fromKm: formData.from_km,
      toKm: formData.to_km,
      modelNo: formData.model_no,
      machineIssue: formData.machine_issue,
      photoUrl: uploadedUrl
    };

    try {
      await axios.post(`${API_BASE_URL}/api/complaints/create`, payload, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      alert('Complaint submitted successfully!');
      setFormData({
        division: '',
        section: '',
        from_km: '',
        to_km: '',
        model_no: '',
        machine_issue: '',
        file: null
      });
    } catch (err) {
      console.error('Complaint submission failed:', err);
      alert('Failed to submit complaint!');
    }
  };

  return (

    <AdminLayout>
      <h2>Register Customer Complaint</h2>
      <form className="dispatch-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="division">Division</label>
          <input type="text" id="division" name="division" value={formData.division} onChange={handleChange} required />
        </div>

        <div className="form-group">
          <label htmlFor="section">Section</label>
          <input type="text" id="section" name="section" value={formData.section} onChange={handleChange} required />
        </div>

        <div className="form-group">
          <label htmlFor="from_km">From KM</label>
          <input type="number" id="from_km" name="from_km" value={formData.from_km} onChange={handleChange} required />
        </div>

        <div className="form-group">
          <label htmlFor="to_km">To KM</label>
          <input type="number" id="to_km" name="to_km" value={formData.to_km} onChange={handleChange} required />
        </div>

        <button type="button" className="submit-btn" onClick={fetchModelNo}>
          Fetch Model No
        </button>

        <div className="form-group">
          <label htmlFor="model_no">Model No</label>
          <input type="text" id="model_no" name="model_no" value={formData.model_no} readOnly />
        </div>

        <div className="issue-container">
  <div className="form-group issue-remark">
    <label htmlFor="machine_issue">Describe Issue</label>
    <textarea
      id="machine_issue"
      name="machine_issue"
      value={formData.machine_issue}
      onChange={handleChange}
      required
    />
  </div>

          <div className="form-group issue-upload">
            <label htmlFor="file">Upload Photo/Video</label>
            <input type="file" id="file" name="file" accept="image/*,video/*" onChange={handleFileChange} />
            
            {formData.file && (
              <div className="file-preview">
                <p>Preview:</p>
                {formData.file.type.startsWith('image/') && (
                  <img
                    src={URL.createObjectURL(formData.file)}
                    alt="Preview"
                    className="image-preview"
                  />
                )}
                <button
                  type="button"
                  className="download-btn"
                  onClick={() => {
                    const link = document.createElement('a');
                    link.href = URL.createObjectURL(formData.file);
                    link.download = formData.file.name;
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                  }}
                >
                  Download
                </button>
              </div>
            )}
          </div>
        </div>

        <button type="submit" className="submit-btn">
          Submit Complaint
        </button>

        <button
          type="button"
          className="submit-btn"
          onClick={() => alert('Assign Technician functionality to be built next')}
        >
          Assign Technician
        </button>
      </form>
    </AdminLayout>
  );
};

export default AddComplaintForm;
