import React, { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";

export default function MachineQRCode() {
  const { serialNo } = useParams();
  const [qrImage, setQrImage] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const printRef = useRef();

  useEffect(() => {
    const fetchQR = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await axios.get(
          `${API_BASE_URL}/api/machines-production/${serialNo}/qrcode`,
          {
            headers: { Authorization: `Bearer ${token}` },
            responseType: "blob",
          }
        );
        const imageUrl = URL.createObjectURL(res.data);
        setQrImage(imageUrl);
      } catch (err) {
        setError("Failed to load QR code.");
      } finally {
        setLoading(false);
      }
    };
    fetchQR();
  }, [serialNo]);

  const handlePrint = () => {
    const printContents = printRef.current.innerHTML;
    const win = window.open("", "", "width=600,height=700");
    win.document.write(`
      <html>
        <head>
          <title>Print QR Code</title>
          <style>
            body { text-align: center; font-family: Arial; margin-top: 40px; }
            img { width: 300px; height: 300px; }
          </style>
        </head>
        <body>
          ${printContents}
        </body>
      </html>
    `);
    win.document.close();
    win.focus();
    win.print();
    win.close();
  };

  if (loading) return <AdminLayout><div>Loading QR Code...</div></AdminLayout>;
  if (error) return <AdminLayout><div>{error}</div></AdminLayout>;

  return (
    <AdminLayout>
      <div style={{ textAlign: "center", marginTop: "20px" }}>
        <h2>QR Code for Machine: {serialNo}</h2>

        <div ref={printRef}>
           <h3>Machine Serial No: {serialNo}</h3>
          {qrImage && (
            <img
              src={qrImage}
              alt={`QR Code for machine ${serialNo}`}
              style={{ width: "300px", height: "300px" }}
            />
          )}
        </div>

        <button
          onClick={handlePrint}
          style={{
            marginTop: "20px",
            padding: "10px 20px",
            fontSize: "16px",
            cursor: "pointer",
          }}
        >
          🖨️ Print QR Code
        </button>
      </div>
    </AdminLayout>
  );
}
