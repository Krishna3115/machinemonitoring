import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./GreaseRefillForm.css";
import API_BASE_URL from "../../apiConfig";

export default function GreaseRefillForm() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  const [form, setForm] = useState({
    modelNo: "",
    remainingGreaseKg: "",
    remainingGreasePhoto: "",
    filledGreaseKg: "",
    filledGreasePhoto: "",
    isFullTank: false,
    remarks: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleRadioChange = (value) => {
    setForm((prev) => ({ ...prev, isFullTank: value }));
  };

  const handleImageUpload = async (fieldName, file) => {
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post(`${API_BASE_URL}/api/upload/image`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      const fileUrl = `${API_BASE_URL}${response.data.url}`;
      setForm((prev) => ({ ...prev, [fieldName]: fileUrl }));
    } catch (error) {
      alert("❌ Failed to upload image");
      console.error(error);
    }
  };

  const handleSubmit = async () => {
    if (!userId) {
      alert("User ID not found.");
      return;
    }

    try {
      await axios.post(`${API_BASE_URL}/api/grease-refill/submit`, {
        ...form,
        submittedBy: userId,
      });
      alert("✅ Grease refill report submitted!");
      navigate("/technician");
    } catch (error) {
      alert("❌ Submission failed.");
      console.error(error);
    }
  };

  return (
    <div className="form-container">
      <button className="back-button-top" onClick={() => navigate(-1)}>🔙</button>
      <h2>🛢️ Grease Refill Report</h2>

      <div className="form-grid">
        <div className="form-group">
          <label>Machine Number</label>
          <input
            type="text"
            name="modelNo"
            value={form.modelNo}
            onChange={handleChange}
            placeholder="Enter machine number"
          />
        </div>

        <div className="form-group">
          <label>Actual Grease (kg)</label>
          <input
            type="number"
            name="remainingGreaseKg"
            value={form.remainingGreaseKg}
            onChange={handleChange}
            placeholder="e.g. 5.5"
          />
        </div>

        <div className="form-group">
          <label>Upload Actual Grease Photo</label>
          <input
            type="file"
            accept="image/*"
            onChange={(e) => handleImageUpload("remainingGreasePhoto", e.target.files[0])}
          />
          {form.remainingGreasePhoto && (
            <img src={form.remainingGreasePhoto} alt="Remaining" className="preview-image" />
          )}
        </div>

        <div className="form-group">
          <label>Actual Filled Grease (kg)</label>
          <input
            type="number"
            name="filledGreaseKg"
            value={form.filledGreaseKg}
            onChange={handleChange}
            placeholder="e.g. 3.0"
          />
        </div>

        <div className="form-group">
          <label>Upload Filled Grease Photo</label>
          <input
            type="file"
            accept="image/*"
            onChange={(e) => handleImageUpload("filledGreasePhoto", e.target.files[0])}
          />
          {form.filledGreasePhoto && (
            <img src={form.filledGreasePhoto} alt="Filled" className="preview-image" />
          )}
        </div>

        <div className="form-group">
          <label>Is Tank Filled to Full?</label>
          <div className="radio-group">
            <label>
              <input
                type="radio"
                name="isFullTank"
                checked={form.isFullTank === true}
                onChange={() => handleRadioChange(true)}
              />
              Yes
            </label>
            <label>
              <input
                type="radio"
                name="isFullTank"
                checked={form.isFullTank === false}
                onChange={() => handleRadioChange(false)}
              />
              No
            </label>
          </div>

          <div className="form-group">
            <label>Remarks (optional)</label>
            <textarea
              name="remarks"
              value={form.remarks}
              onChange={handleChange}
              placeholder="Any notes..."
            />
          </div>
        </div>
      </div>

      <button className="submit-button" onClick={handleSubmit}>
        🚀 Submit Grease Refill Report
      </button>
    </div>
  );
}
