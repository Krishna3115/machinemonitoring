import React, { useState } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
import "./CompleteInstallationForm.css";
import API_BASE_URL from "../../apiConfig";

export default function CompleteInstallationForm() {
  const navigate = useNavigate();
  const location = useLocation();
  const modelNo = location.state?.modelNo || "";

  const [form, setForm] = useState({
    modelNo: modelNo,
    section: "",
    curveNo: "",
    poleNo: "",
    fromKm: "",
    toKm: "",
    rhLhRadius: "",
    srDen: "",
    lineSection: "",
    pwi: "",
    machineStatus: "",
    greaseLevel: "",
    greaseLevelPhotoUrl: "",
    wheelCount: null,
    timeCount: null,
    remarks: ""
  });

  // Field labels mapping
  const fieldLabels = {
    section: "Block Section",
    curveNo: "Curve No",
    poleNo: "Pole No",
    fromKm: "From KM",
    toKm: "To KM",
    rhLhRadius: "RH/LH Radius",
    srDen: "SR/Den",
    lineSection: "Line Section",
    pwi: "PWI Section",
    machineStatus: "Machine Status",
    greaseLevel: "Grease Level",
    wheelCount: "Wheel Count",
    timeCount: "Time Count",
    remarks: "Remarks"
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    const numberFields = ["wheelCount", "timeCount"];

    setForm({
      ...form,
      [name]: numberFields.includes(name)
        ? value === "" ? null : parseInt(value)
        : value
    });
  };

  const handleImageUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post(
        `${API_BASE_URL}/api/upload/image`,
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
      );

      const fileUrl = `${API_BASE_URL}${response.data.url}`;
      setForm((prevForm) => ({
        ...prevForm,
        greaseLevelPhotoUrl: fileUrl
      }));
    } catch (err) {
      alert("Image upload failed.");
    }
  };

  const handleSubmit = async () => {
    if (!form.wheelCount || !form.timeCount) {
      alert("Please enter valid numbers for Wheel Count and Time Count.");
      return;
    }

    try {
      await axios.post(
        `${API_BASE_URL}/api/installations/installation/complete`,
        form
      );
      alert("Installation completed successfully.");
      navigate("/technician/start-installation");
    } catch (err) {
      console.error("Error:", err);
      alert("Error completing installation. " + (err.response?.data || ""));
    }
  };

  return (
    <div className="form-container">
      <button className="back-button" onClick={() => navigate("/technician")}>
        ← Back
      </button>

      <h2>📋 Complete Installation - {modelNo}</h2>

      <div className="form-grid">
        {Object.keys(form)
          .filter((key) => key !== "modelNo" && key !== "greaseLevelPhotoUrl")
          .map((key) => (
            <div className="form-group" key={key}>
              <label>{fieldLabels[key] || key}</label>
              <input
                type={
                  key === "wheelCount" || key === "timeCount"
                    ? "number"
                    : "text"
                }
                name={key}
                value={form[key] || ""}
                onChange={handleChange}
                placeholder={`Enter ${fieldLabels[key] || key}`}
              />
            </div>
          ))}

        <div className="form-group">
          <label>Grease Level Photo</label>
          <input
            type="file"
            accept="image/*"
            capture="environment"
            onChange={handleImageUpload}
          />
          {form.greaseLevelPhotoUrl && (
            <img
              src={form.greaseLevelPhotoUrl}
              alt="Grease Level Preview"
              className="preview-image"
            />
          )}
        </div>
      </div>

      <button className="submit-button" onClick={handleSubmit}>
        ✅ Submit Installation
      </button>
    </div>
  );
}
