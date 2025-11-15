import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import './MachineProduction.css';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';
import Select from 'react-select';
import Toast from '../components/Toast'; // ✅ Toast component

const AddProduction = () => {
  const [formData, setFormData] = useState({
    jobCardNo: '',
    machineSerialNo: '',
    motorNo: '',
    sensorNo: '',
    applicatorNo: '',
    batteryNo: '',
    solarChargeControllerNo: '',
    solarPanelNo1: '',
    solarPanelNo2: '',
    cabinetNo: '',
    batchCounterNo: '',
    mcbNo: '',
    gearPumpNo: ''
  });

  const [jobCards, setJobCards] = useState([]);
  const [pendingSerials, setPendingSerials] = useState([]);
  const [errors, setErrors] = useState({});
  const inputRefs = useRef({});
  const [toast, setToast] = useState({ message: '', type: 'success' });

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
  };

  const fieldOrder = [
    'motorNo',
    'sensorNo',
    'applicatorNo',
    'batteryNo',
    'solarChargeControllerNo',
    'solarPanelNo1',
    'solarPanelNo2',
    'cabinetNo',
    'batchCounterNo',
    'mcbNo',
    'gearPumpNo'
  ];

  const fieldLabels = {
    jobCardNo: 'Job Card No',
    machineSerialNo: 'Machine Serial No',
    motorNo: 'Motor No',
    sensorNo: 'Sensor No',
    applicatorNo: 'Applicator No',
    batteryNo: 'Battery No',
    solarChargeControllerNo: 'Solar Charge Controller No',
    solarPanelNo1: 'Solar Panel No. 1',
    solarPanelNo2: 'Solar Panel No. 2',
    cabinetNo: 'Cabinet No',
    batchCounterNo: 'Batch Counter No',
    mcbNo: 'MCB No',
    gearPumpNo: 'Gear Pump No'
  };

  // ✅ Fetch all Job Cards
  useEffect(() => {
    fetchJobCards();
  }, []);

  const fetchJobCards = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${API_BASE_URL}/api/job-cards/in-progress/details`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setJobCards(res.data);
    } catch (err) {
      console.error('❌ Error fetching job cards:', err);
      showToast('Failed to load job cards.', 'error');
    }
  };

  // ✅ Fetch pending serials for selected Job Card
  useEffect(() => {
   const fetchPendingSerials = async () => {
  if (!formData.jobCardNo) {
    setPendingSerials([]);
    return;
  }
  try {
    const res = await axios.get(`${API_BASE_URL}/api/job-cards/${formData.jobCardNo}/pending-serials`);
    console.log('📦 API pending serials response:', res.data);

    // ✅ Directly access the correct key from API response
    const serials = res.data.pending_serial_numbers || [];

    setPendingSerials(serials);
  } catch (err) {
    console.error('❌ Error loading machine serial numbers:', err);
    setPendingSerials([]);
  }
};
    fetchPendingSerials();
  }, [formData.jobCardNo]);

  // ✅ Handle text input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));

    if (name === 'jobCardNo') {
      setFormData(prev => ({ ...prev, machineSerialNo: '' }));
    }

    if (errors[name]) {
      const newErrors = { ...errors };
      delete newErrors[name];
      setErrors(newErrors);
    }
  };

  // ✅ Handle dropdown changes
  const handleSelectChange = (selectedOption, { name }) => {
    const value = selectedOption ? selectedOption.value : '';
    setFormData(prev => ({
      ...prev,
      [name]: value,
      ...(name === 'jobCardNo' ? { machineSerialNo: '' } : {})
    }));

    if (errors[name]) {
      const newErrors = { ...errors };
      delete newErrors[name];
      setErrors(newErrors);
    }
  };

  // ✅ Handle form submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    // Validate main fields
    if (!formData.jobCardNo || !formData.machineSerialNo) {
      setErrors({
        jobCardNo: !formData.jobCardNo ? 'Batch card is required' : undefined,
        machineSerialNo: !formData.machineSerialNo ? 'Please select a machine serial number' : undefined
      });
      return;
    }

    // Validate component fields
    for (let field of fieldOrder) {
      if (!formData[field]?.trim()) {
        setErrors({ [field]: 'This field is required' });
        inputRefs.current[field]?.scrollIntoView({ behavior: 'smooth', block: 'center' });
        inputRefs.current[field]?.focus();
        setTimeout(() => setErrors({}), 2000);
        return;
      }
    }

    try {
      await axios.post(`${API_BASE_URL}/api/machines-production/create`, formData, {
        headers: { Authorization: `Bearer ${token}` }
      });

      showToast('✅ Machine production saved successfully!', 'success');

      setFormData({
        jobCardNo: '',
        machineSerialNo: '',
        motorNo: '',
        sensorNo: '',
        applicatorNo: '',
        batteryNo: '',
        solarChargeControllerNo: '',
        solarPanelNo1: '',
        solarPanelNo2: '',
        cabinetNo: '',
        batchCounterNo: '',
        mcbNo: '',
        gearPumpNo: ''
      });

      setPendingSerials([]);
      setErrors({});
      fetchJobCards();
    } catch (err) {
      console.error('❌ Error submitting data:', err);
      showToast('❌ Failed to save machine production.', 'error');
    }
  };

  // ✅ Render input fields
  const renderInput = (name, label) => (
    <div className="form-row" key={name}>
      <label htmlFor={name} className="required">{label}</label>
      <input
        id={name}
        name={name}
        type="text"
        value={formData[name]}
        onChange={handleChange}
        ref={(el) => (inputRefs.current[name] = el)}
        className={errors[name] ? 'input-error' : ''}
      />
      {errors[name] && <span className="error-text">{errors[name]}</span>}
    </div>
  );

  // ✅ Format date [YYYY,MM,DD] -> DD-MM-YYYY
  const formatDate = (dateArray) => {
    if (!Array.isArray(dateArray) || dateArray.length < 3) return '';
    const [year, month, day] = dateArray;
    return `${String(day).padStart(2, '0')}-${String(month).padStart(2, '0')}-${year}`;
  };

  // ✅ Map job cards properly for dropdown
  const jobCardOptions = jobCards.map(j => ({
    value: j.job_card_number,
    label: `${j.job_card_number} (${j.produced_count}/${j.quantity}) — ${formatDate(j.start_date)} ➜ ${formatDate(j.end_date)}`
  }));

  const serialOptions = pendingSerials.map(sn => ({
    value: sn,
    label: sn
  }));

  return (
    <AdminLayout>
      <div className="dispatch-form-container">
        <h2>Add Machine Production</h2>
        <form className="dispatch-form" onSubmit={handleSubmit}>

          {/* ✅ Job Card Dropdown */}
          <div className="form-row">
            <label className="required" htmlFor="jobCardNo">Batch Card No</label>
            <Select
              id="jobCardNo"
              name="jobCardNo"
              options={jobCardOptions}
              value={jobCardOptions.find(opt => opt.value === formData.jobCardNo) || null}
              onChange={handleSelectChange}
              classNamePrefix="react-select"
              isSearchable
              placeholder="Select Batch Card"
              styles={{
                control: (base) => ({
                  ...base,
                  minHeight: 42,
                  fontSize: 15,
                  borderRadius: 4,
                  borderColor: errors.jobCardNo ? 'red' : '#ccc'
                })
              }}
            />
            {errors.jobCardNo && <span className="error-text">{errors.jobCardNo}</span>}
          </div>

          {/* ✅ Machine Serial No Dropdown */}
          <div className="form-row">
            <label className="required" htmlFor="machineSerialNo">Machine Serial No</label>
            <Select
              id="machineSerialNo"
              name="machineSerialNo"
              options={serialOptions}
              value={serialOptions.find(opt => opt.value === formData.machineSerialNo) || null}
              onChange={handleSelectChange}
              classNamePrefix="react-select"
              isSearchable
              placeholder="Select Machine Serial No"
              isDisabled={!formData.jobCardNo || serialOptions.length === 0}
              noOptionsMessage={() => "No available serial numbers"}
              styles={{
                control: (base) => ({
                  ...base,
                  minHeight: 42,
                  fontSize: 15,
                  borderRadius: 4,
                  borderColor: errors.machineSerialNo ? 'red' : '#ccc'
                })
              }}
            />
            {errors.machineSerialNo && <span className="error-text">{errors.machineSerialNo}</span>}
            {formData.jobCardNo && pendingSerials.length === 0 && (
              <span className="error-text">No available machine serial numbers for this job card.</span>
            )}
          </div>

          {/* ✅ Other Input Fields */}
          {fieldOrder.map(field => renderInput(field, fieldLabels[field]))}

          <div className="form-actions">
            <button type="submit" className="submit-btn">Save Production</button>
          </div>
        </form>
      </div>

      {/* ✅ Toast Message */}
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

export default AddProduction;
