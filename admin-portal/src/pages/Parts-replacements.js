import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Parts-replacements.css';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';

const PartsReplacementForm = () => {
  const [formData, setFormData] = useState({
    machine_no: '',
    datetime: '',
    reason: '',
    dispatch_method: '',
    technician_assigned_id: '',
    replacing_technician_id: '',
    courier_name: '',
    tracking_number: '',
    courier_contact: '',
    parts: [
      {
        part_name: '',
        old_part_no: '',
        replaced_part_no: '',
        machine_serial_no: ''
      }
    ]
  });

  const [technicians, setTechnicians] = useState([]);

  useEffect(() => {
    const fetchTechnicians = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${API_BASE_URL}/api/users/technicians`, {
          headers: { Authorization: `Bearer ${token}` }
        });

        const activeTechs = response.data.filter(tech => !tech.isBlocked);
        setTechnicians(activeTechs);
      } catch (err) {
        console.error('Failed to fetch active technicians:', err);
      }
    };

    fetchTechnicians();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === 'technician_assigned_id' || name === 'replacing_technician_id') {
      setFormData(prev => ({ ...prev, [name]: value ? Number(value) : '' }));
    } else {
      setFormData(prev => ({ ...prev, [name]: value }));
    }
  };

  const handlePartChange = (index, field, value) => {
    const updatedParts = [...formData.parts];
    updatedParts[index][field] = value;
    setFormData(prev => ({ ...prev, parts: updatedParts }));
  };

  const addPartRow = () => {
    setFormData(prev => ({
      ...prev,
      parts: [...prev.parts, { part_name: '', old_part_no: '', replaced_part_no: '', machine_serial_no: '' }]
    }));
  };

  const removePartRow = (index) => {
    const updatedParts = [...formData.parts];
    updatedParts.splice(index, 1);
    setFormData(prev => ({ ...prev, parts: updatedParts }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    if (
      formData.dispatch_method === 'With Technician' &&
      formData.technician_assigned_id &&
      formData.replacing_technician_id &&
      formData.technician_assigned_id !== formData.replacing_technician_id
    ) {
      alert('Replacing technician must match the dispatched technician!');
      return;
    }

    try {
      await axios.post(`${API_BASE_URL}/api/parts-replacement/create`, formData, {
        headers: { Authorization: `Bearer ${token}` }
      });

      alert('Replacement request submitted!');
      setFormData({
        machine_no: '',
        datetime: '',
        reason: '',
        dispatch_method: '',
        technician_assigned_id: '',
        replacing_technician_id: '',
        courier_name: '',
        tracking_number: '',
        courier_contact: '',
        parts: [{ part_name: '', old_part_no: '', replaced_part_no: '', machine_serial_no: '' }]
      });
    } catch (err) {
      console.error('Submission failed:', err);
      alert('Failed to submit replacement request.');
    }
  };

  return (
    <AdminLayout>
      <div className="parts-replacement-form">
        <h2>🛠️ Part Replacement Request</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="required">Machine No.</label>
            <input
              type="text"
              name="machine_no"
              value={formData.machine_no}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label className="required">Date & Time</label>
            <input
              type="datetime-local"
              name="datetime"
              value={formData.datetime}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label className="required">Parts Information</label>
            {formData.parts.map((part, index) => (
              <div key={index} className="part-row">
                <input
                  type="text"
                  placeholder="Part Name"
                  value={part.part_name}
                  onChange={(e) => handlePartChange(index, 'part_name', e.target.value)}
                  required
                />
                <input
                  type="text"
                  placeholder="Old Part No."
                  value={part.old_part_no}
                  onChange={(e) => handlePartChange(index, 'old_part_no', e.target.value)}
                  required
                />
                <input
                  type="text"
                  placeholder="Replaced Part No."
                  value={part.replaced_part_no}
                  onChange={(e) => handlePartChange(index, 'replaced_part_no', e.target.value)}
                  required
                />
                <input
                  type="text"
                  placeholder="Machine Serial No."
                  value={part.machine_serial_no}
                  onChange={(e) => handlePartChange(index, 'machine_serial_no', e.target.value)}
                  required
                />
                {index > 0 && (
                  <button type="button" className="remove-btn" onClick={() => removePartRow(index)}>
                    ❌
                  </button>
                )}
              </div>
            ))}
            <button type="button" className="add-btn" onClick={addPartRow}>
              ➕ Add Part
            </button>
          </div>

          <div className="form-group">
            <label className="required">Reason for Replacement</label>
            <textarea
              name="reason"
              value={formData.reason}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label className="required">Dispatch Method</label>
            <select
              name="dispatch_method"
              value={formData.dispatch_method}
              onChange={handleChange}
              required
            >
              <option value="">-- Select Method --</option>
              <option value="With Technician">With Technician</option>
              <option value="Via Courier">Via Courier</option>
            </select>
          </div>

          {formData.dispatch_method === 'With Technician' && (
            <div className="form-group">
              <label className="required">Technician Assigned</label>
              <select
                name="technician_assigned_id"
                value={formData.technician_assigned_id}
                onChange={handleChange}
                required
              >
                <option value="">-- Select Technician --</option>
                {technicians.map(tech => (
                  <option key={tech.id} value={tech.id}>
                    {tech.name}
                  </option>
                ))}
              </select>
            </div>
          )}

          {formData.dispatch_method === 'Via Courier' && (
            <div className="flex-group">
              <div className="form-group">
                <label className="required">Courier Name</label>
                <input
                  type="text"
                  name="courier_name"
                  value={formData.courier_name}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label className="required">Tracking Number</label>
                <input
                  type="text"
                  name="tracking_number"
                  value={formData.tracking_number}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Courier Contact</label>
                <input
                  type="text"
                  name="courier_contact"
                  value={formData.courier_contact}
                  onChange={handleChange}
                />
              </div>
            </div>
          )}

          <div className="form-group">
            <label className="required">Replacing Technician</label>
            <select
              name="replacing_technician_id"
              value={formData.replacing_technician_id}
              onChange={handleChange}
              required
            >
              <option value="">-- Select Technician --</option>
              {technicians.map(tech => (
                <option key={tech.id} value={tech.id}>
                  {tech.name}
                </option>
              ))}
            </select>
          </div>

          <button type="submit" className="submit-btn">🚀 Submit Request</button>
        </form>
      </div>
    </AdminLayout>
  );
};

export default PartsReplacementForm;
