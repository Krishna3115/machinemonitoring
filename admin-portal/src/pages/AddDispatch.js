import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AddDispatch.css';
import AdminLayout from '../components/AdminLayout';
import Select from 'react-select';
import API_BASE_URL from '../apiConfig';
import Toast from '../components/Toast';

const AddDispatch = () => {
  const [formData, setFormData] = useState({
    dispatchDate: '',
    location: 'CIL Plant 2',
    finalInspectionDoneBy: '',
    division: '',
    section: '',
    purchaseOrderId: ''
  });

  const [purchaseOrders, setPurchaseOrders] = useState([]);
  const [selectedPO, setSelectedPO] = useState(null);

  const [availableMachines, setAvailableMachines] = useState([]);
  const [selectedMachines, setSelectedMachines] = useState([]);

  // ✅ Now only one PDI file for all selected machines
  const [pdiFile, setPdiFile] = useState(null);
  const [errors, setErrors] = useState({});

  const [toast, setToast] = useState({ message: '', type: 'success' });
  const showToast = (message, type = 'success') => setToast({ message, type });

  const token = localStorage.getItem('token');

  useEffect(() => {
    // Fetch pending POs with authorization
    axios.get(`${API_BASE_URL}/api/po-orders/pending`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        if (res.data) setPurchaseOrders(res.data);
      })
      .catch(err => {
        console.error('Failed to load POs:', err.response ? err.response.data : err.message);
        showToast('❌ Failed to load POs.', 'error');
      });

    // Fetch available machines
    axios.get(`${API_BASE_URL}/api/machines-production/available`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setAvailableMachines(res.data))
      .catch(err => {
        console.error('Failed to load machines:', err.response ? err.response.data : err.message);
        showToast('❌ Failed to load available machines.', 'error');
      });
  }, [token]);

  const poOptions = purchaseOrders.map(po => ({
    value: po.id,
    label: `${po.po_number} (Remaining: ${po.quantity - po.dispatched_count})`,
    fullData: po
  }));

  const machineOptions = availableMachines.map(machine => ({
    value: machine.id,
    label: `${machine.machineSerialNo} - ${machine.jobCardNo}`
  }));

  const handlePOChange = (selectedOption) => {
    setSelectedPO(selectedOption);
    setFormData(prev => ({
      ...prev,
      purchaseOrderId: selectedOption ? selectedOption.value : ''
    }));
  };

  const handleMachineChange = (selectedOptions) => {
    setSelectedMachines(selectedOptions || []);
  };

  // ✅ Single file handler
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setPdiFile(file);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // ✅ Validation for one file only
  const validate = () => {
    if (!pdiFile) {
      setErrors({ pdi: 'Please upload the PDI report.' });
      return false;
    }
    setErrors({});
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    const submitData = new FormData();
    submitData.append('purchaseOrderId', formData.purchaseOrderId);
    submitData.append('dispatchDate', new Date(formData.dispatchDate).toISOString());
    submitData.append('location', formData.location);
    submitData.append('finalInspectionDoneBy', formData.finalInspectionDoneBy);
    submitData.append('division', formData.division);
    submitData.append('section', formData.section);

    // ✅ Attach all selected machines with the same file
    selectedMachines.forEach(machine => {
      submitData.append('selectedModelNos', machine.label.split(' - ')[0]);
      submitData.append('pdiReports', pdiFile);
    });

    try {
      await axios.post(`${API_BASE_URL}/api/machines/dispatch`, submitData, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data'
        }
      });

      showToast('✅ Dispatch saved successfully!', 'success');

      // Reset form
      setFormData({
        dispatchDate: '',
        location: 'CIL Plant 2',
        finalInspectionDoneBy: '',
        division: '',
        section: '',
        purchaseOrderId: ''
      });
      setSelectedPO(null);
      setSelectedMachines([]);
      setPdiFile(null);
      setErrors({});
    } catch (err) {
      console.error('Dispatch error:', err.response ? err.response.data : err.message);
      showToast('❌ Failed to dispatch machines.', 'error');
    }
  };

  return (
    <AdminLayout>
      <div className="dispatch-form-container">
        <h2>Add Machine Dispatch</h2>
        <form className="dispatch-form" onSubmit={handleSubmit}>

          <div className="form-row">
            <label>Select L.O.A</label>
            <Select
              options={poOptions}
              onChange={handlePOChange}
              value={selectedPO}
              placeholder="Select L.O.A No."
              isClearable
            />
          </div>

          {selectedPO?.fullData && (
            <div className="po-details">
              <p><strong>Total:</strong> {selectedPO.fullData.quantity}</p>
              <p><strong>Dispatched:</strong> {selectedPO.fullData.dispatched_count}</p>
              <p><strong>Remaining:</strong> {selectedPO.fullData.quantity - selectedPO.fullData.dispatched_count}</p>
            </div>
          )}

          <div className="form-row">
            <label>Dispatch Date</label>
            <input
              type="datetime-local"
              name="dispatchDate"
              value={formData.dispatchDate}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-row">
            <label>Location</label>
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-row">
            <label>Final Quality Check</label>
            <input
              type="text"
              name="finalInspectionDoneBy"
              value={formData.finalInspectionDoneBy}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-row">
            <label>Division</label>
            <input
              type="text"
              name="division"
              value={formData.division}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-row">
            <label>Section</label>
            <input
              type="text"
              name="section"
              value={formData.section}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-row">
            <label>Select Machines for Dispatch</label>
            <Select
              options={machineOptions}
              value={selectedMachines}
              onChange={handleMachineChange}
              isMulti
              placeholder="Select available machines"
            />
          </div>

          {/* ✅ Single upload field for all selected machines */}
          {selectedMachines.length > 0 && (
            <div className="form-row">
              <label>Upload PDI Report for Selected Machines</label>
              <input type="file" accept=".pdf" onChange={handleFileChange} required />
            </div>
          )}

          {errors.pdi && <div className="error-text">{errors.pdi}</div>}

          <div className="form-actions">
            <button type="submit" className="submit-btn">Save Dispatch</button>
          </div>
        </form>
      </div>

      {toast.message && (
        <Toast
          message={toast.message}
          type={toast.type}
          duration={3000}
          onClose={() => setToast({ message: '', type: 'success' })}
        />
      )}
    </AdminLayout>
  );
};

export default AddDispatch;
