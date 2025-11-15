import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import './PendingDeliveries.css';
import API_BASE_URL from '../apiConfig';
import Toast from '../components/Toast'; // adjust path if needed

const addReminder = ({ id, type, message }) => {
  const list = JSON.parse(localStorage.getItem("reminderList") || "[]");
  const alreadyExists = list.some(item => item.id === id && item.type === type);
  if (!alreadyExists) {
    list.push({
      id,
      type,
      message,
      timestamp: Date.now(),
      lastDismissed: 0
    });
    localStorage.setItem("reminderList", JSON.stringify(list));
  }
};

const removeReminder = (id, type) => {
  const list = JSON.parse(localStorage.getItem("reminderList") || "[]");
  const updated = list.filter(item => !(item.id === id && item.type === type));
  localStorage.setItem("reminderList", JSON.stringify(updated));
};

export default function PendingDeliveries() {
  const [machines, setMachines] = useState([]);
  const [deliveryData, setDeliveryData] = useState({});
  const [filterMode, setFilterMode] = useState('NOTHING_SET');
  const [toast, setToast] = useState({ message: '', type: 'success' });

  useEffect(() => {
    fetchPendingMachines();
  }, [filterMode]);

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
  };

  const sendEmailNotification = async ({ subject, body, file }) => {
    const formData = new FormData();
    formData.append("subject", subject);
    formData.append("body", body);
    if (file) {
      formData.append("attachment", file);
    }

    try {
      const res = await axios.post(`${API_BASE_URL}/api/email/send-delivery-status`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      showToast("Email sent successfully!", "success");
      console.log("Email Response:", res.data);
    } catch (err) {
      console.error("Error sending email:", err);
      showToast("Failed to send email: " + err.message, "error");
    }
  };

  const parseBackendDate = (dateArrayOrString) => {
    if (!dateArrayOrString) return null;
    if (Array.isArray(dateArrayOrString)) {
      const [year, month, day, hour = 0, minute = 0, second = 0] = dateArrayOrString;
      return new Date(year, month - 1, day, hour, minute, second); // ✅ month - 1
    }
    return new Date(dateArrayOrString);
  };

  const fetchPendingMachines = async () => {
    try {
      const token = localStorage.getItem("token");
      let url = `${API_BASE_URL}/api/machines/status/DISPATCHED`;
      if (filterMode === 'DATE_ONLY') {
        url = `${API_BASE_URL}/api/machines/pending-receiving-letter`;
      }

      const res = await axios.get(url, {
        headers: { Authorization: `Bearer ${token}` }
      });

      const formattedData = res.data.map(m => ({
        ...m,
        dispatchDate: parseBackendDate(m.dispatch_date),
        deliveredDate: parseBackendDate(m.delivered_date),
        receivingLetterUrl: m.receiving_letter_url
      }));

      if (filterMode === 'NOTHING_SET' || filterMode === 'DATE_ONLY') {
        formattedData.forEach(machine => {
          if (machine.deliveredDate && !machine.receivingLetterUrl) {
            addReminder({
              id: machine.id,
              type: "pending-delivery",
              message: `Reminder: Please upload receiving letter for Machine ${machine.model_no}`,
            });
          }
        });
      }

      setMachines(formattedData);
    } catch (err) {
      console.error("Error fetching machines", err);
      showToast("Failed to fetch machines.", "error");
    }
  };

  const handleDateChange = (id, date) => {
    setDeliveryData(prev => ({
      ...prev,
      [id]: {
        ...prev[id],
        date
      }
    }));
  };

  const handleFileChange = (id, file) => {
    setDeliveryData(prev => ({
      ...prev,
      [id]: {
        ...prev[id],
        file
      }
    }));
  };

  const handleDeliveryDateUpdate = async (id) => {
    const data = deliveryData[id];
    const formData = new FormData();

    if (data?.date) formData.append("deliveredDate", data.date);
    if (data?.file) formData.append("receivingLetter", data.file);

    if (!data?.date && !data?.file) {
      showToast("Please select a delivered date or upload a receiving letter.", "error");
      return;
    }

    const machine = machines.find(m => m.id === id);
    const modelNo = machine?.model_no;

    if (!modelNo) {
      showToast("Model number is missing. Cannot proceed.", "error");
      return;
    }

    const deliveredDateValue = data?.date || machine?.deliveredDate || null;

    try {
      const token = localStorage.getItem("token");
      await axios.put(
        `${API_BASE_URL}/api/machines/${id}/deliver`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'multipart/form-data'
          }
        }
      );

      showToast("Saved successfully!", "success");
      fetchPendingMachines();

      setDeliveryData(prev => {
        const updated = { ...prev };
        delete updated[id];
        return updated;
      });

      if (deliveredDateValue && !data?.file) {
        addReminder({
          id,
          type: "pending-delivery",
          message: `Machine ${modelNo} is pending for receiving letter.`,
        });
      } else if (data?.file) {
        removeReminder(id, "pending-delivery");
      }

      const deliveredDateFormatted = deliveredDateValue
        ? new Date(deliveredDateValue).toLocaleString('en-GB')
        : 'Not provided';

      let emailSubject = `Delivery Status Updated - Machine ${modelNo}`;
      let emailBody = `Machine Serial No: ${modelNo}\nDelivery Status has been updated.\nDelivered Date: ${deliveredDateFormatted}`;

      if (!data?.file) {
        emailBody += `\n\nNote: Receiving letter is pending.`;
      }

      await sendEmailNotification({
        subject: emailSubject,
        body: emailBody,
        file: data?.file || null,
      });
    } catch (err) {
      console.error("Error saving delivery info", err);
      showToast("Failed to save delivery info.", "error");
    }
  };

  const getStatusTag = (machine) => {
    const { deliveredDate, receivingLetterUrl } = machine;
    if (deliveredDate && receivingLetterUrl) return <span className="status-complete">Completed</span>;
    if (deliveredDate && !receivingLetterUrl) return <span className="status-pending">Pending Letter</span>;
    if (!deliveredDate && !receivingLetterUrl) return <span className="status-missing">Pending All</span>;
    return null;
  };

  return (
    <AdminLayout>
      <div className="pending-deliveries-container">
        <h2>Pending Deliveries</h2>

        <div className="filter-buttons">
          <button
            onClick={() => setFilterMode('NOTHING_SET')}
            className={filterMode === 'NOTHING_SET' ? 'active' : ''}
          >
            Pending Date & Letter
          </button>

          <button
            onClick={() => setFilterMode('DATE_ONLY')}
            className={filterMode === 'DATE_ONLY' ? 'active' : ''}
          >
            Pending Receiving Letter
          </button>
        </div>

        <table className="deliveries-table">
          <thead>
            <tr>
              <th>Status</th>
              <th>Machine Serial No</th>
              <th>Machine Type</th>
              <th>Dispatch Location</th>
              <th>Dispatch Date</th>
              <th>Days Since Dispatch</th>
              <th>Delivered Date</th>
              <th>Receiving Letter</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {machines.map(machine => {
              const dispatchDate = machine.dispatchDate ? new Date(machine.dispatchDate) : null;
              const today = new Date();
              const daysSinceDispatch = dispatchDate
                ? Math.floor((today - dispatchDate) / (1000 * 60 * 60 * 24))
                : '-';

              const rowClass = machine.deliveredDate && !machine.receivingLetterUrl
                ? 'pending-letter'
                : (!machine.deliveredDate && !machine.receivingLetterUrl)
                  ? 'pending-all'
                  : '';

              return (
                <tr key={machine.id} className={rowClass}>
                  <td>{getStatusTag(machine)}</td>
                  <td>{machine.model_no}</td>
                  <td>{machine.machine_name}</td>
                  <td>{machine.division}</td>
                  <td>{dispatchDate ? dispatchDate.toLocaleDateString('en-GB') : 'N/A'}</td>
                  <td>{dispatchDate ? `${daysSinceDispatch} days` : 'N/A'}</td>
                  <td>
                    <input
                      type="datetime-local"
                      value={deliveryData[machine.id]?.date || ''}
                      onChange={(e) => handleDateChange(machine.id, e.target.value)}
                    />
                    {machine.deliveredDate && (
                      <div className="existing-data">
                        <small>Saved: {new Date(machine.deliveredDate).toLocaleString('en-GB')}</small>
                      </div>
                    )}
                  </td>
                  <td>
                    <input
                      type="file"
                      accept="application/pdf"
                      onChange={(e) => handleFileChange(machine.id, e.target.files[0])}
                    />
                    {machine.receivingLetterUrl && (
                      <div className="existing-data">
                        <a href={machine.receivingLetterUrl} target="_blank" rel="noopener noreferrer">
                          View
                        </a>
                      </div>
                    )}
                  </td>
                  <td>
                    <button className="update-button" onClick={() => handleDeliveryDateUpdate(machine.id)}>
                      Save
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
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
}
