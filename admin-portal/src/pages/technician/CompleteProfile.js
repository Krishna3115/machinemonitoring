import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./CompleteProfile.css";
import API_BASE_URL from "../../apiConfig";

export default function CompleteProfile() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId"); // ✅ Make sure this is set during login

  const [formData, setFormData] = useState({
    address: "",
    emergencyContactNumber: "",
    profilePhotoUrl: "",
    idProofUrl: "",
  });

  const [profilePhotoFile, setProfilePhotoFile] = useState(null);
  const [idProofFile, setIdProofFile] = useState(null);

  useEffect(() => {
    // ✅ Safety check
    if (!userId) {
      alert("User ID not found. Please log in again.");
      navigate("/login");
    }
  }, [userId, navigate]);

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleFileUpload = async (file, type) => {
    const data = new FormData();
    data.append("file", file);

    const endpoint =
      type === "image"
        ? `${API_BASE_URL}/api/upload/image`
        : `${API_BASE_URL}/api/upload/pdf`;

    const res = await axios.post(endpoint, data, {
      headers: { "Content-Type": "multipart/form-data" },
    });

    return res.data.url; // backend should return { "url": "https://..." }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (!userId) {
        alert("User ID missing. Please log in again.");
        navigate("/login");
        return;
      }

      let uploadedProfileUrl = formData.profilePhotoUrl;
      let uploadedIdProofUrl = formData.idProofUrl;

      // ✅ Upload files if selected
      if (profilePhotoFile) {
        uploadedProfileUrl = await handleFileUpload(profilePhotoFile, "image");
      }
      if (idProofFile) {
        uploadedIdProofUrl = await handleFileUpload(idProofFile, "pdf");
      }

      const payload = {
        address: formData.address,
        emergencyContactNumber: formData.emergencyContactNumber,
        profilePhotoUrl: uploadedProfileUrl,
        idProofUrl: uploadedIdProofUrl,
      };

      console.log("Submitting profile with:", payload, "User ID:", userId);

      await axios.post(
        `${API_BASE_URL}/api/users/complete-profile/${userId}`,
        payload
      );

      localStorage.setItem("profileComplete", "true");
      alert("Profile completed successfully!");
      navigate("/technician");
    } catch (err) {
      console.error("Profile submission error:", err);
      alert(
        "Failed to complete profile: " +
          (err.response?.data?.message || err.message)
      );
    }
  };

  return (
    <div className="dispatch-form">
      <h2 style={{ textAlign: "center", marginBottom: "20px" }}>
        Complete Your Profile
      </h2>

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Address</label>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Profile Photo (.jpeg)</label>
          <input
            type="file"
            accept="image/*"
            onChange={(e) => setProfilePhotoFile(e.target.files[0])}
            required
          />
        </div>

        <div className="form-group">
          <label>ID Proof (.pdf)</label>
          <input
            type="file"
            accept="application/pdf"
            onChange={(e) => setIdProofFile(e.target.files[0])}
          />
        </div>

        <div className="form-group">
          <label>Emergency Contact Number</label>
          <input
            type="text"
            name="emergencyContactNumber"
            value={formData.emergencyContactNumber}
            onChange={handleChange}
            required
          />
        </div>

        <button className="submit-btn" type="submit">
          Submit Profile
        </button>
      </form>
    </div>
  );
}
