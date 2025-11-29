import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';
import Select from 'react-select';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import Toast from '../components/Toast';
import { useNavigate } from 'react-router-dom'; // <-- Import useNavigate
import './MachineProduction.css';

const AddProduction = () => {
  const navigate = useNavigate(); // <-- Hook to navigate programmatically

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
    gearPumpNo: '',
    productionStartDate: null,
    productionEndDate: null,
    junctionBoxBatchNo: '',
    junctionBoxBatchDate: '',
    sensorAssyBatchNo: '',
    sensorAssyBatchDate: '',
    tmpAssyBatchNo: '',
    tmpAssyBatchDate: '',
    applicatorAssyBatchNo: '',
    applicatorAssyBatchDate: '',
    solarPanelAssyBatchNo: '',
    solarPanelAssyBatchDate: ''
  });

  const [jobCards, setJobCards] = useState([]);
  const [pendingSerials, setPendingSerials] = useState([]);
  const [errors, setErrors] = useState({});
  const inputRefs = useRef({});
  const [toast, setToast] = useState({ message: '', type: 'success' });

  const showToast = (message, type = 'success') => setToast({ message, type });

  const fieldOrder = [
    'motorNo', 'sensorNo', 'applicatorNo', 'batteryNo', 'solarChargeControllerNo',
    'solarPanelNo1', 'solarPanelNo2', 'cabinetNo', 'batchCounterNo',
    'mcbNo', 'gearPumpNo', 'productionStartDate', 'productionEndDate'
  ];

  const assemblyFields = [
    { label: 'Offline Sub Assy :-', headingOnly: true },
    { label: 'Offline Assy:- ', headingOnly: true },
    { label: 'Junction Box (JB) Assy', batchNo: 'junctionBoxBatchNo', batchDate: 'junctionBoxBatchDate' },
    { label: 'Sensor Assy', batchNo: 'sensorAssyBatchNo', batchDate: 'sensorAssyBatchDate' },
    { label: 'Tank Motor Pump (TMP) Assy', batchNo: 'tmpAssyBatchNo', batchDate: 'tmpAssyBatchDate' },
    { label: 'Applicator Assy', batchNo: 'applicatorAssyBatchNo', batchDate: 'applicatorAssyBatchDate' },
    { label: 'Solar Panel Assy', batchNo: 'solarPanelAssyBatchNo', batchDate: 'solarPanelAssyBatchDate' }
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
    gearPumpNo: 'Gear Pump No',
    productionStartDate: 'Production Start Date',
    productionEndDate: 'Production End Date'
  };

  useEffect(() => { fetchJobCards(); }, []);

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

  useEffect(() => {
    const fetchPendingSerials = async () => {
      if (!formData.jobCardNo) { setPendingSerials([]); return; }
      try {
        const res = await axios.get(`${API_BASE_URL}/api/job-cards/${formData.jobCardNo}/pending-serials`);
        const serials = res.data.pending_serial_numbers || [];
        setPendingSerials(serials);
      } catch (err) {
        console.error('❌ Error loading machine serial numbers:', err);
        setPendingSerials([]);
      }
    };
    fetchPendingSerials();
  }, [formData.jobCardNo]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) setErrors(prev => { const copy = {...prev}; delete copy[name]; return copy; });
  };

  const handleSelectChange = (selectedOption, { name }) => {
    const value = selectedOption ? selectedOption.value : '';
    setFormData(prev => ({ ...prev, [name]: value, ...(name === 'jobCardNo' ? { machineSerialNo: '' } : {}) }));
    if (errors[name]) setErrors(prev => { const copy = {...prev}; delete copy[name]; return copy; });
  };

  const parseDateForBackend = (dateObj) => {
    if (!dateObj) return '';
    const day = String(dateObj.getDate()).padStart(2,'0');
    const month = String(dateObj.getMonth() + 1).padStart(2,'0');
    const year = dateObj.getFullYear();
    return `${year}-${month}-${day}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.jobCardNo || !formData.machineSerialNo) {
      setErrors({
        jobCardNo: !formData.jobCardNo ? 'Batch card is required' : undefined,
        machineSerialNo: !formData.machineSerialNo ? 'Select machine serial number' : undefined
      });
      return;
    }

    for (let field of fieldOrder) {
      if (!formData[field] || (typeof formData[field] === 'string' && !formData[field].trim())) {
        setErrors({ [field]: 'This field is required' });
        inputRefs.current[field]?.scrollIntoView({ behavior: 'smooth', block: 'center' });
        inputRefs.current[field]?.focus();
        setTimeout(() => setErrors({}), 2000);
        return;
      }
    }

    try {
      const token = localStorage.getItem('token');
      const user = JSON.parse(localStorage.getItem("user"));

      const payload = {
        ...formData,
        productionStartDate: parseDateForBackend(formData.productionStartDate),
        productionEndDate: parseDateForBackend(formData.productionEndDate),
        submitted_by_id: user?.userId,
        submitted_by_name: user?.username ? user.username.split(' ')[0] : ''
      };

      await axios.post(`${API_BASE_URL}/api/machines-production/create`, payload, {
        headers: { Authorization: `Bearer ${token}` }
      });

      showToast('✅ Machine production saved successfully!', 'success');

      setFormData(prev => Object.keys(prev).reduce((acc, key) => ({ ...acc, [key]: key.includes('Date') ? null : '' }), {}));
      setPendingSerials([]);
      setErrors({});
      fetchJobCards();
    } catch (err) {
      console.error('❌ Error submitting data:', err);
      showToast('❌ Failed to save machine production.', 'error');
    }
  };

  const renderInput = (name, label, placeholder='') => {
    const isDateField = name === 'productionStartDate' || name === 'productionEndDate';

    if (isDateField) {
      return (
        <div className="form-row" key={name}>
          <label htmlFor={name} className="required">{label}</label>
          <DatePicker
            id={name}
            selected={formData[name]}
            onChange={(date) => setFormData(prev => ({ ...prev, [name]: date }))}
            dateFormat="dd/MM/yyyy"
            placeholderText="DD/MM/YYYY"
            className={errors[name] ? 'input-error' : ''}
          />
          {errors[name] && <span className="error-text">{errors[name]}</span>}
        </div>
      );
    }

    return (
      <div className="form-row" key={name}>
        <label htmlFor={name} className="required">{label}</label>
        <input
          id={name}
          name={name}
          type="text"
          value={formData[name]}
          onChange={handleChange}
          ref={el => inputRefs.current[name] = el}
          placeholder={placeholder}
          className={errors[name] ? 'input-error' : ''}
        />
        {errors[name] && <span className="error-text">{errors[name]}</span>}
      </div>
    );
  };

  const formatDate = (arr) =>
    arr?.length === 3 ? `${String(arr[2]).padStart(2,'0')}-${String(arr[1]).padStart(2,'0')}-${arr[0]}` : '';

  const jobCardOptions = jobCards.map(j => ({
    value: j.job_card_number,
    label: `${j.job_card_number} (${j.produced_count}/${j.quantity}) — ${formatDate(j.start_date)} ➜ ${formatDate(j.end_date)}`
  }));

  const serialOptions = pendingSerials.map(sn => ({ value: sn, label: sn }));

  return (
    <AdminLayout>
      <div className="dispatch-form-container">
        {/* Back Button */}
        <button
          className="back-btn"
          type="button"
          onClick={() => navigate(-1)} // <- Go back to previous page
        >
          &larr; Back
        </button>

        <h2>Add Machine Production</h2>
        <form className="dispatch-form" onSubmit={handleSubmit}>
          <div className="form-row">
            <label className="required">Batch Card No</label>
            <Select
              name="jobCardNo"
              options={jobCardOptions}
              value={jobCardOptions.find(opt => opt.value === formData.jobCardNo) || null}
              onChange={handleSelectChange}
              classNamePrefix="react-select"
              isSearchable
              placeholder="Select Batch Card"
            />
            {errors.jobCardNo && <span className="error-text">{errors.jobCardNo}</span>}
          </div>

          <div className="form-row">
            <label className="required">Machine Serial No</label>
            <Select
              name="machineSerialNo"
              options={serialOptions}
              value={serialOptions.find(opt => opt.value === formData.machineSerialNo) || null}
              onChange={handleSelectChange}
              isSearchable
              placeholder="Select Machine Serial No"
              isDisabled={!formData.jobCardNo || serialOptions.length === 0}
            />
            {errors.machineSerialNo && <span className="error-text">{errors.machineSerialNo}</span>}
          </div>

          {fieldOrder.map(f => renderInput(f, fieldLabels[f]))}

          {assemblyFields.map(f => f.headingOnly ? (
            <div className="assembly-heading" key={f.label}>{f.label} Details</div>
          ) : (
            <div className="assembly-row" key={f.label}>
              <label>{f.label}</label>
              <div className="assembly-inputs">
                <input
                  type="text"
                  placeholder="Batch No."
                  name={f.batchNo}
                  value={formData[f.batchNo]}
                  onChange={handleChange}
                />
                <input
                  type="date"
                  placeholder="Batch Date"
                  name={f.batchDate}
                  value={formData[f.batchDate]}
                  onChange={handleChange}
                />
              </div>
            </div>
          ))}

          <div className="form-actions">
            <button type="submit" className="submit-btn">Save Production</button>
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

export default AddProduction;
