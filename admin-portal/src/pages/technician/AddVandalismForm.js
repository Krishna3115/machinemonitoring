import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./AddVandalismForm.css";
import API_BASE_URL from "../../apiConfig";

export default function VandalismReportForm() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  const [forms, setForms] = useState([
    {
      modelNo: "",
      componentName: "",
      issueDescription: "",
      photoUrls: [], // Multiple photos support
      isDamaged: false,
      damageTypes: [], // Multi-select parts
    },
  ]);

  const handleChange = (index, e) => {
    const { name, value } = e.target;
    const newForms = [...forms];
    newForms[index][name] = value;
    setForms(newForms);
  };

  const handleDamageChange = (index, isDamaged) => {
    const updatedForms = [...forms];
    updatedForms[index].isDamaged = isDamaged;
    if (isDamaged) {
      updatedForms[index].issueDescription = "";
    } else {
      updatedForms[index].componentName = "";
      // You can choose to clear damageTypes or keep them
      // updatedForms[index].damageTypes = [];
    }
    setForms(updatedForms);
  };

  const handleDamageTypeToggle = (index, type) => {
  const updatedForms = [...forms];
  const selectedTypes = updatedForms[index].damageTypes;

  if (selectedTypes.includes(type)) {
    updatedForms[index].damageTypes = selectedTypes.filter((t) => t !== type);

    // Clear otherComponentName if "Other" is deselected
    if (type === "Other (please specify)") {
      updatedForms[index].otherComponentName = "";
    }
  } else {
    updatedForms[index].damageTypes = [...selectedTypes, type];
  }

  // Update componentName field
  const others = updatedForms[index].otherComponentName
    ? [`Other: ${updatedForms[index].otherComponentName}`]
    : [];

  updatedForms[index].componentName = updatedForms[index].damageTypes
    .filter((t) => t !== "Other (please specify)")
    .concat(others)
    .join(", ");

  setForms(updatedForms);
};


  const handleImageUpload = async (index, e) => {
  const files = Array.from(e.target.files);
  if (!files.length) return;

  try {
    const uploadPromises = files.map(async (file) => {
      const formData = new FormData();
      formData.append("file", file);

      const response = await axios.post(`${API_BASE_URL}/api/upload/image`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      return `${API_BASE_URL}${response.data.url}`;
    });

    const uploadedUrls = await Promise.all(uploadPromises);

    const updatedForms = [...forms];
    updatedForms[index].photoUrls = [...updatedForms[index].photoUrls, ...uploadedUrls];
    setForms(updatedForms);

    // ✅ Add these logs here
    console.log("Uploaded image URLs:", uploadedUrls);
    console.log("Form after upload:", updatedForms[index]);

  } catch (err) {
    console.error("Image upload failed:", err);
    alert("Image upload failed.");
  }
};


  const removeForm = (indexToRemove) => {
    if (forms.length === 1) return;
    setForms((prev) => prev.filter((_, i) => i !== indexToRemove));
  };

  const handleSubmit = async () => {
    if (!userId) {
      alert("User ID not found. Please log in again.");
      return;
    }

    const payload = forms.map((form) => ({
      ...form,
      reportedByUserId: userId,
    }));

    try {
      await axios.post(`${API_BASE_URL}/api/vandalism/submit-multiple`, payload);
      alert("✅ All vandalism reports submitted successfully!");
      navigate("/technician/dashboard");
    } catch (err) {
      console.error("Submission error:", err);
      alert("❌ Failed to submit reports.");
    }
  };

  const handleBack = () => {
    navigate(-1);
  };

  return (
    <div className="form-container">
      <button className="back-button-top" onClick={handleBack}>
        🔙
      </button>

      <h2>🚨 Vandalism Reports</h2>

      {forms.map((form, index) => (
        <div className="form-grid" key={index}>
          <h4>Report #{index + 1}</h4>

          <div className="form-group">
            <label>Machine Model No</label>
            <input
              type="text"
              name="modelNo"
              value={form.modelNo}
              onChange={(e) => handleChange(index, e)}
              placeholder="Enter model number"
            />
          </div>

          <div className="form-group">
            <label>Is the component damaged, but the machine is still working?</label>
            <div className="radio-group">
              <label>
                <input
                  type="radio"
                  name={`isDamaged-${index}`}
                  value="yes"
                  checked={form.isDamaged === true}
                  onChange={() => handleDamageChange(index, true)}
                />
                Yes
              </label>
              <label>
                <input
                  type="radio"
                  name={`isDamaged-${index}`}
                  value="no"
                  checked={form.isDamaged === false}
                  onChange={() => handleDamageChange(index, false)}
                />
              </label>
             No
               </div>
          </div>

          {/* Always show multi-select parts checkboxes */}
          <div className="damage-type-group">
  <label>Select Parts (multiple allowed):</label>

  {/* Primary options */}
  <div className="checkbox-group">
    {["Solar Panel", "Applicator", "Sensor", "Cabinet"].map((type) => (
      <label key={type} className="checkbox-label">
        <input
          type="checkbox"
          value={type}
          checked={form.damageTypes.includes(type)}
          onChange={() => handleDamageTypeToggle(index, type)}
        />
        {type}
      </label>
    ))}
  </div>

  {/* Toggle for more options */}
  <div>
    <button
      type="button"
      className="more-components-toggle"
      onClick={() => {
        const updatedForms = [...forms];
        updatedForms[index].showMore = !updatedForms[index].showMore;
        setForms(updatedForms);
      }}
    >
      {form.showMore ? "Hide More Components ▲" : "More Components ▼"}
    </button>
  </div>

  {/* More components (secondary options) */}
  {form.showMore && (
    <div className="checkbox-group more-components">
      {[
        "Sensor Wire",
        "Rail Clamp Blocks",
        "Door Lock",
        "Battery",
        "Hydraulic Pump",
        "DC Motor",
        "Junction Box",
        "Horse Pipe",
        "Other (please specify)",
      ].map((type) => (
        <label key={type} className="checkbox-label">
          <input
            type="checkbox"
            value={type}
            checked={form.damageTypes.includes(type)}
            onChange={() => handleDamageTypeToggle(index, type)}
          />
          {type}
        </label>
      ))}

      {/* If "Other" is selected, show a text field */}
      {form.damageTypes.includes("Other (please specify)") && (
        <input
          type="text"
          placeholder="Please specify"
          value={
            form.otherComponentName || ""
          }
          onChange={(e) => {
            const updatedForms = [...forms];
            updatedForms[index].otherComponentName = e.target.value;

            // Automatically update componentName field to include 'Other'
            updatedForms[index].componentName = updatedForms[index].damageTypes
              .filter((t) => t !== "Other (please specify)")
              .concat([`Other: ${e.target.value}`])
              .join(", ");

                setForms(updatedForms);
              }}
            />
          )}
        </div>
      )}
    </div>


          {form.isDamaged ? (
            <p className="info-line">🛠️ Machine is working</p>
          ) : (
            <div className="form-group">
              <label>Issue Description</label>
              <textarea
                name="issueDescription"
                value={form.issueDescription}
                onChange={(e) => handleChange(index, e)}
                placeholder="Describe the issue"
              />
            </div>
          )}

          <div className="form-group">
            <label>Upload Photos</label>
            <input
              type="file"
              accept="image/*"
              multiple
              onChange={(e) => handleImageUpload(index, e)}
            />
            <div className="image-preview-container">
              {form.photoUrls.map((url, i) => (
                <img
                  key={i}
                  src={url}
                  alt={`Uploaded ${i + 1}`}
                  className="preview-image"
                />
              ))}
            </div>
          </div>

          <hr />
        </div>
      ))}

   
      <button className="submit-button" onClick={handleSubmit}>
        🚀 Submit All Reports
      </button>
    </div>
  );
}
