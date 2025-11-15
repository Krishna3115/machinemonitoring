// AddJobCard.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AddBatchCard.css';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';
import Toast from '../components/Toast';

const AddJobCard = () => {
  const [formData, setFormData] = useState({
    machineType: '',
    quantity: '',
    startDate: '',
    endDate: '',
    processLayout: ''
  });

  const [loaList, setLoaList] = useState([]);
  const [selectedLoaIndex, setSelectedLoaIndex] = useState("");
  const [errors, setErrors] = useState({});
  const [jobPreview, setJobPreview] = useState(null);
  const [toastMessage, setToastMessage] = useState('');
  const [toastType, setToastType] = useState(''); // 'success' | 'error'
  // Already printed LOAs (stored in localStorage)
  const getPrintedLoas = () => JSON.parse(localStorage.getItem('printedLoas') || "[]");


  const token = localStorage.getItem('token');

  // Utility: format date for printing
  const formatDatePrint = (dateStr) => {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
  };

  // Fetch LOA list on mount
  useEffect(() => {
    fetchLoaList();
  }, []);

  const fetchLoaList = async () => {
  try {
    const res = await axios.get(`${API_BASE_URL}/api/production-planning/loa-details`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    const mappedData = res.data.map(item => ({
      poNumber: item.po_number,
      plannedQuantity: item.planned_quantity,
      startDate: item.start_date && item.start_date.length >= 3
        ? new Date(item.start_date[0], item.start_date[1] - 1, item.start_date[2]).toISOString().slice(0, 10)
        : '',
      endDate: item.end_date && item.end_date.length >= 3
        ? new Date(item.end_date[0], item.end_date[1] - 1, item.end_date[2]).toISOString().slice(0, 10)
        : ''
    }));

    const printedLoas = getPrintedLoas();
    const filteredData = mappedData.filter(item => !printedLoas.includes(item.poNumber));

    setLoaList(filteredData);
  } catch (err) {
    console.error('Error fetching L.O.A list:', err);
  }
};

// Replace setSelectedLoa usage in handleLOAChange
const handleLOAChange = (e) => {
  const index = Number(e.target.value); // ✅ FIX: convert to Number for safe array access
    setSelectedLoaIndex(index);

  if (index === "") return;

  const selected = loaList[index];
  if (selected) {
    setFormData(prev => ({
      ...prev,
      quantity: selected.plannedQuantity || '',
      startDate: selected.startDate || '',
      endDate: selected.endDate || ''
    }));
  } else {
    setFormData(prev => ({
      ...prev,
      quantity: '',
      startDate: '',
      endDate: ''
    }));
  }
};


  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.machineType) newErrors.machineType = 'Machine type is required';
    if (!formData.quantity || formData.quantity <= 0) newErrors.quantity = 'Enter a valid quantity';
    if (!formData.startDate) newErrors.startDate = 'Start date is required';
    if (!formData.endDate) newErrors.endDate = 'End date is required';
    if (!formData.processLayout) newErrors.processLayout = 'Process layout is required';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    try {
      const payload = {
        machine_type: formData.machineType,
        quantity: parseInt(formData.quantity),
        start_date: formData.startDate,
        end_date: formData.endDate,
        process_layout: formData.processLayout,
        loa_number: loaList[selectedLoaIndex]?.poNumber

      };

      const res = await axios.post(`${API_BASE_URL}/api/job-cards`, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      setJobPreview(res.data);
      setToastMessage('✅ Batch card created successfully!');
      setToastType('success');
    } catch (err) {
      console.error('Error creating Batch card:', err);
      setToastMessage('❌ Failed to create batch card.');
      setToastType('error');
    }
  };

  // ✅ Updated handlePrint with LOA removal logic
  const handlePrint = () => {
    if (!jobPreview || !jobPreview.machine_serial_numbers) return;

    const machineSerialNumbers = jobPreview.machine_serial_numbers;
    if (!machineSerialNumbers || machineSerialNumbers.length === 0) return;

    const printWindow = window.open('', '_blank');

    const printPages = machineSerialNumbers.map((serial) => {
      const jobCardNo = serial.match(/\d+$/)?.[0] || serial;

      return `
        <div class="page">
          <div class="header">
            <h1>CHAKRADHAR INDUSTRIES</h1>
            <h3>Job Card</h3>
          </div>

          <table class="info-table">
            <tr>
              <td><strong>Batch Card No:</strong></td>
              <td colspan="2">${jobPreview.job_card_number}</td>
              <td><strong>Job Card No:</strong></td>
              <td colspan="2">${jobCardNo}</td>
            </tr>
            <tr>
              <td><strong>Machine Serial No:</strong></td>
              <td>${serial}</td>
              <td><strong>Job Card Date:</strong></td>
              <td>${formatDatePrint(new Date())}</td>
              <td><strong>Machine Type:</strong></td>
              <td>${jobPreview.machine_type}</td>
            </tr>
            <tr>
              <td><strong>Production Start Date:</strong></td>
              <td colspan="2">${formatDatePrint(jobPreview.start_date)}</td>
              <td><strong>Production End Date:</strong></td>
              <td colspan="3">${formatDatePrint(jobPreview.end_date)}</td>
            </tr>
          </table>

          <table class="component-table">
            <thead>
              <tr>
                <th>SR.NO.</th><th>Particular</th><th>Serial Number</th>
                <th>SR.NO.</th><th>Particular</th><th>Serial Number</th>
              </tr>
            </thead>
            <tbody>
              <tr><td>1</td><td>Motor</td><td></td><td>6</td><td>Cabinet</td><td></td></tr>
              <tr><td>2</td><td>Sensor</td><td></td><td>7</td><td>Batch Counter</td><td></td></tr>
              <tr><td>3</td><td>Applicator</td><td></td><td>8</td><td>MCB</td><td></td></tr>
              <tr><td>4</td><td>Battery</td><td></td><td>9</td><td>Gear Pump</td><td></td></tr>
              <tr><td>5</td><td>Solar Charge Controller</td><td></td><td>10</td><td>Solar Panel No.1 & 2</td><td></td></tr>
            </tbody>
          </table>

          <table class="assembly-table">
            <thead>
              <tr>
                <th>SR.NO</th><th>Sub Assembly</th><th>Assembly Start Date</th>
                <th>FITTER</th><th>Assembly Completed Date</th><th>Checked By</th><th>Verified By</th>
              </tr>
            </thead>
            <tbody>
              <tr><td>A</td><td>Dispense Unit Assembly</td><td></td><td></td><td></td><td></td><td></td></tr>
              <tr><td>B</td><td>Cabinet Assembly</td><td></td><td></td><td></td><td></td><td></td></tr>
              <tr><td>C</td><td>Control Panel Assembly</td><td></td><td></td><td></td><td></td><td></td></tr>
              <tr><td>D</td><td>Labelling Activity</td><td></td><td></td><td></td><td></td><td></td></tr>
              <tr><td>E</td><td>Assembly Kit</td><td></td><td></td><td></td><td></td><td></td></tr>
            </tbody>
          </table>

          <div class="footer">
            <div>Produced By:</div>
            <div>Checked By:</div>
            <div>Approved By:</div>
          </div>
        </div>
      `;
    }).join('<div class="page-break"></div>');

    const printContent = `
      <html>
        <head>
          <title>Batch Card Print</title>
          <style>
            @page { size: A4 portrait; margin: 5mm; }
            body { font-family: Arial, sans-serif; background: white; }
            .page { padding: 20px; border: 2px solid black; height: 275mm; width: 195mm; margin: auto; page-break-after: always; }
            .header { text-align: center; margin-bottom: 20px; }
            .info-table, .component-table, .assembly-table { width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 14px; }
            .info-table td, .component-table td, .assembly-table td, .component-table th, .assembly-table th { border: 1px solid #000; padding: 6px; text-align: center; }
            .component-table th, .assembly-table th { background: #f2f2f2; }
            .footer { margin-top: 40px; display: flex; justify-content: space-between; font-size: 13px; }
            .footer div { width: 30%; text-align: center; margin-top: 60px; }
            .page-break { page-break-before: always; }
          </style>
        </head>
        <body>
          ${printPages}
          <script>window.onload = () => window.print();</script>
        </body>
      </html>
    `;

    printWindow.document.write(printContent);
    printWindow.document.close();
    printWindow.focus();
    printWindow.print();

    const selectedLoa = loaList[selectedLoaIndex];


    // ✅ After printing and window close, remove LOA
    const interval = setInterval(() => {
      if (printWindow.closed) {
        clearInterval(interval);

        // Remove LOA from dropdown
        setLoaList(prev => prev.filter((_, idx) => idx != selectedLoaIndex));
       
       if (selectedLoa) {
          const printedLoas = getPrintedLoas();
          printedLoas.push(selectedLoa.poNumber);
          localStorage.setItem('printedLoas', JSON.stringify(printedLoas));
        }

        // Reset fields
        setSelectedLoaIndex('');
        setFormData({
          machineType: '',
          quantity: '',
          startDate: '',
          endDate: '',
          processLayout: ''
        });
        setJobPreview(null);

        // Success toast
        setToastMessage('✅ LOA processed and removed from list!');
        setToastType('success');
      }
    }, 500);
  };

  const formatDate = (dateStr) => dateStr ? dateStr.slice(0, 10) : '';

  return (
    <AdminLayout>
      <div className="dispatch-form-container">
        <h2 className="page-title">CREATE BATCH ORDER</h2>

        <form className="dispatch-form" onSubmit={handleSubmit}>
          <div className="form-row">
            <label>Select L.O.A No.</label>
           <select name="loaNumber" value={selectedLoaIndex} onChange={handleLOAChange} required>
            <option value="">Select L.O.A No.</option>
            {loaList.map((item, index) => (
              <option key={index} value={index}>
                {`${item.poNumber} — Planned: ${item.plannedQuantity} — ${formatDate(item.startDate)} to ${formatDate(item.endDate)}`}
              </option>
            ))}
            </select>


          </div>

          <div className="form-row">
            <label>Machine Type</label>
            <select name="machineType" value={formData.machineType} onChange={handleChange} required>
              <option value="">Select Type</option>
              <option value="TBL Electronic">TBL Electronic</option>
              <option value="TBL Hydraulic">TBL Hydraulic</option>
            </select>
            {errors.machineType && <div className="error-text">{errors.machineType}</div>}
          </div>

          <div className="form-row">
            <label>Quantity</label>
            <input type="number" name="quantity" value={formData.quantity} onChange={handleChange} min="1" required />
            {errors.quantity && <div className="error-text">{errors.quantity}</div>}
          </div>

          <div className="form-row">
            <label>Start Date</label>
            <input type="date" name="startDate" value={formData.startDate} onChange={handleChange} required />
            {errors.startDate && <div className="error-text">{errors.startDate}</div>}
          </div>

          <div className="form-row">
            <label>End Date</label>
            <input type="date" name="endDate" value={formData.endDate} onChange={handleChange} required />
            {errors.endDate && <div className="error-text">{errors.endDate}</div>}
          </div>

          <div className="form-row">
            <label>Process Layout</label>
            <textarea name="processLayout" value={formData.processLayout} onChange={handleChange} rows="4" />
            {errors.processLayout && <div className="error-text">{errors.processLayout}</div>}
          </div>

          <div className="form-actions">
            <button type="submit" className="submit-btn">Create Batch Card</button>
          </div>
        </form>

        {jobPreview && (
          <div className="job-preview">
            <h3>Batch Card Preview</h3>
            <p><strong>Batch Order No:</strong> {jobPreview.job_card_number}</p>
            <p><strong>Machine Type:</strong> {jobPreview.machine_type}</p>
            <p><strong>Quantity:</strong> {jobPreview.quantity}</p>
            <p><strong>Start Date:</strong> {formatDate(jobPreview.start_date)}</p>
            <p><strong>End Date:</strong> {formatDate(jobPreview.end_date)}</p>
            <div style={{ marginTop: '10px' }}>
              <button className="submit-btn" onClick={handlePrint}>Print</button>
            </div>
          </div>
        )}
      </div>

      <Toast
        message={toastMessage}
        type={toastType}
        onClose={() => setToastMessage('')}
      />
    </AdminLayout>
  );
};

export default AddJobCard;
