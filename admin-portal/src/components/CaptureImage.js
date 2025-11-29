import React, { useState, useRef, useEffect } from "react";
import "./CaptureImage.css";

export default function CaptureImage({ label, onUpload }) {
  const [showCamera, setShowCamera] = useState(false);
  const [capturedImage, setCapturedImage] = useState(null);
  const [previewImage, setPreviewImage] = useState(null);
  const videoRef = useRef(null);
  const canvasRef = useRef(null);

  // Open camera modal
  const openCamera = () => {
    setShowCamera(true);
  };

  // Start camera stream
  useEffect(() => {
    let stream;
    if (showCamera) {
      navigator.mediaDevices.getUserMedia({ video: true })
        .then((s) => {
          stream = s;
          if (videoRef.current) videoRef.current.srcObject = stream;
        })
        .catch((err) => {
          console.error("Camera error:", err);
          alert("Cannot access camera");
          setShowCamera(false);
        });
    }
    return () => {
      if (stream) {
        stream.getTracks().forEach(track => track.stop());
      }
    };
  }, [showCamera]);

  // Take photo and add watermark
  const handleTakePhoto = () => {
    const video = videoRef.current;
    const canvas = canvasRef.current;
    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;
    const ctx = canvas.getContext("2d");
    ctx.drawImage(video, 0, 0);

    // Add watermark
    ctx.font = "20px Arial";
    ctx.fillStyle = "red";
    const now = new Date();
    const dateStr = `${String(now.getDate()).padStart(2,'0')}/${String(now.getMonth()+1).padStart(2,'0')}/${now.getFullYear()} ${String(now.getHours()).padStart(2,'0')}:${String(now.getMinutes()).padStart(2,'0')}`;
    ctx.fillText(dateStr, 10, canvas.height - 20);

    const imageData = canvas.toDataURL("image/png");
    setPreviewImage(imageData);
    setCapturedImage(imageData);
  };

  // Save photo to parent form
  const handleSave = () => {
    if (capturedImage) {
      onUpload(capturedImage);
      setShowCamera(false);
    }
  };

  // Retake photo
  const handleRetake = () => {
    setCapturedImage(null);
    setPreviewImage(null);
  };

  return (
    <div className="capture-container">
      <label>{label}</label>
      <button type="button" className="take-photo-button" onClick={openCamera}>
        📸 Take Photo
      </button>
      {previewImage && (
        <img src={previewImage} alt="Preview" className="preview-image" />
      )}

      {showCamera && (
        <div className="camera-modal">
          <div className="camera-content">
            {!previewImage && <video ref={videoRef} autoPlay playsInline />}
            {previewImage && <img src={previewImage} alt="Preview" />}
            <canvas ref={canvasRef} style={{ display: "none" }} />
            <div className="camera-buttons">
              {!previewImage && (
                <button onClick={handleTakePhoto}>Take Photo</button>
              )}
              {previewImage && (
                <>
                  <button onClick={handleSave}>Save</button>
                  <button onClick={handleRetake}>Retake</button>
                </>
              )}
              <button onClick={() => setShowCamera(false)}>Close</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
