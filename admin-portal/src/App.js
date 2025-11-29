import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import ReminderPopup from "./components/ReminderPopup"; // Import at top



// Import of Admins

import Loader from './components/Loader';
import Login from './pages/Login';
import AdminPanel from './pages/AdminPanel';
import SuperAdminPanel from './pages/SuperAdminPanel';
import PrivateRoute from './utils/PrivateRoute';
import TechnicianList from "./pages/TechnicianList";
import AddDispatch from './pages/AddDispatch';
import AddNewTechnician from './pages/AddNewTechnician';
import ActivationCodeTab from './pages/ActivationCodeTab';
import BlockTechnicianList from './pages/BlockTechnicianList';
import PendingDeliveries from './pages/PendingDeliveries';
import DeliveredMachines from './pages/DeliveredMachines';
import Customer_complaints from './pages/Customer_complaints';
import MaintenanceDueList from './pages/MaintenanceDueList';
import SiteInspectionPendingList from './pages/SitePendingInspection';
import InstallationInProcess from './pages/InstallationInProcess';
import VandalismReportList from './pages/VandalismReportList';
import ActiveMachinesList from './pages/ActiveMachineList';
import PurchaseOA from './pages/PurchaseOA';
import DispatchReportTable from './pages/DispatchReportTable';
import InstallationReport from './pages/InstallationReport';
import AddBatchCard from './pages/AddBatchCard';
import AddFrequencyForm from './pages/AddFrequencyForm';
import MachineProduction from './pages/MachineProduction';
import MaintenanceReportTable from './pages/MaintenanceReportTable';
import ProfileSettings from './pages/ProfileSettings';
import ChangePassword from './pages/ChangePassword';
import NotificationPreferences from './pages/NotificationPreference';
import SecuritySettings from './pages/SecuritySetting';
import BatchCardProgress from './pages/BatchCardProgress';
import PendingQualityCheck from './pages/PendingQualityCheck';
import AvailableMachineDispatch from './pages/AvailableMachineDispatch';
import MachineQRCode from './pages/MachineQRCode';
import UnderMaintenanceList from './pages/UnderMaintenanceList';
import ContactDetailsPage from './pages/ContactDetailsPage';
import AssignTechnicianPage from './pages/AssignTechnicianPage';
import ProductionPlanningPage from './pages/ProductionPlanning';
import InsuranceClaimDetails from './pages/InsuranceClaimDetails';
import ClaimSummary from './pages/ClaimSummary';
import PartsReplacement from './pages/Parts-replacements';
import MasterReport from './pages/MasterReport';
// Import of Technicians

import TechnicianPanel from './pages/technician/TechnicianPanel'
import CompleteProfile from './pages/technician/CompleteProfile';
import StartInstallation from './pages/technician/StartInstallation';
import CompleteInstallationForm from './pages/technician/CompleteInstallationForm';
import ComplaintCountList from './pages/ComplaintCountList';
import AddVandalismReportForm from './pages/technician/AddVandalismForm';
import StartMaintenance from './pages/technician/StartMaintenance';
import InstantHelpContact from './pages/technician/InstantHelpContact';
import CompleteMaintenanceForm from './pages/technician/CompleteMaintenanceForm';
import EmergencySupport from './pages/technician/EmergencySupport';
import GreaseRefillForm from './pages/technician/GreaseRefillForm';
import AssignUserTaskPage from './pages/technician/AssignUserTaskPage';
import InsuranceClaimList from './pages/InsuranceClaimList';
import ReplacedPartsConfirmationForm from './pages/technician/ReplacedPartsConfirmationForm'
import EditPurchaseOrder from './pages/EditPurchaseOrder';
import TaskHistory from './pages/technician/TaskHistory';













function AppWrapper() {
  const location = useLocation();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    const timer = setTimeout(() => setLoading(false), 1500); // Show loader 500ms on route change
    return () => clearTimeout(timer);
  }, [location]);


  
  return (
    <>
       <ReminderPopup />
      {loading && <Loader />}
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/admin" element={<PrivateRoute role="ADMIN"><AdminPanel /></PrivateRoute>} />
        <Route path="/superadmin" element={<PrivateRoute role="SUPER_ADMIN"><SuperAdminPanel /></PrivateRoute>} />
        <Route path="/admin/technicians" element={<PrivateRoute role="ADMIN"><TechnicianList /></PrivateRoute>} />
        <Route path="/admin/add-dispatch" element={<PrivateRoute role="ADMIN"><AddDispatch /></PrivateRoute>} />
        <Route path="/admin/addNewTechnician" element={<PrivateRoute role="ADMIN"><AddNewTechnician/></PrivateRoute>}/>
        <Route path="/admin/activation-code" element={<PrivateRoute role="ADMIN"><ActivationCodeTab/></PrivateRoute>}/>
        <Route path="/admin/blocked-technicians" element={<PrivateRoute role="ADMIN"><BlockTechnicianList /></PrivateRoute>} />
        <Route path="/admin/pending-deliveries" element={<PrivateRoute role="ADMIN"><PendingDeliveries /></PrivateRoute>} />
        <Route path="/admin/delivered-machines" element={<PrivateRoute role="ADMIN"><DeliveredMachines /></PrivateRoute>} />
        <Route path="/admin/customer_complaint" element={<PrivateRoute role="ADMIN"><Customer_complaints /></PrivateRoute>} />
        <Route path="/admin/maintenance_dueList" element={<PrivateRoute role="ADMIN"><MaintenanceDueList /></PrivateRoute>} />
        <Route path="/admin/site-inspections" element={<PrivateRoute role="ADMIN"><SiteInspectionPendingList /></PrivateRoute>} />
        <Route path="/admin/installations-in-progress" element={<PrivateRoute role="ADMIN"><InstallationInProcess /></PrivateRoute>} />
        <Route path="/admin/active-machines" element={<PrivateRoute role="ADMIN"><ActiveMachinesList /></PrivateRoute>} />
        <Route path="/admin/complaint-count" element={<PrivateRoute role="ADMIN"><ComplaintCountList /></PrivateRoute>} />
        <Route path="/admin/vandalism-report" element={<PrivateRoute role="ADMIN"><VandalismReportList /></PrivateRoute>} />
        <Route path="/admin/purchase-ordered" element={<PrivateRoute role="ADMIN"><PurchaseOA /></PrivateRoute>} />
        <Route path="/admin/dispatch-report" element={<PrivateRoute role="ADMIN"><DispatchReportTable /></PrivateRoute>} />
        <Route path="/admin/installation-report" element={<PrivateRoute role="ADMIN"><InstallationReport /></PrivateRoute>} />
        <Route path="/admin/maintenance-report" element={<PrivateRoute role="ADMIN"><MaintenanceReportTable /></PrivateRoute>} />
        <Route path="/admin/profile-setting" element={<PrivateRoute role="ADMIN"><ProfileSettings /></PrivateRoute>} />
        <Route path="/admin/change-password" element={<PrivateRoute role="ADMIN"><ChangePassword /></PrivateRoute>} />
        <Route path="/admin/notification-preference" element={<PrivateRoute role="ADMIN"><NotificationPreferences /></PrivateRoute>} />
        <Route path="/admin/security-settings" element={<PrivateRoute role="ADMIN"><SecuritySettings /></PrivateRoute>} />
        <Route path="/admin/frequncy-form" element={<PrivateRoute role="ADMIN"><AddFrequencyForm /></PrivateRoute>} />
        <Route path="/admin/add-production-form" element={<PrivateRoute role="ADMIN"><MachineProduction/></PrivateRoute>} />
        <Route path="/admin/add-job-card-details" element={<PrivateRoute role="ADMIN"><AddBatchCard/></PrivateRoute>} />
        <Route path="/admin/job-order-progress" element={<PrivateRoute role="ADMIN"><BatchCardProgress/></PrivateRoute>} />
        <Route path="/admin/pending-quality-check" element={<PrivateRoute role="ADMIN"><PendingQualityCheck/></PrivateRoute>} />
        <Route path="/admin/available-machines-dispatch" element={<PrivateRoute role="ADMIN"><AvailableMachineDispatch/></PrivateRoute>} />
        <Route path="/admin/:serialNo/qrcode" element={<PrivateRoute role="ADMIN"><MachineQRCode/></PrivateRoute>} />
        <Route path="/admin/machine-undermaintenance" element={<PrivateRoute role="ADMIN"><UnderMaintenanceList/></PrivateRoute>} />
        <Route path="/admin/contact-details" element={<PrivateRoute role="ADMIN"><ContactDetailsPage/></PrivateRoute>} />
        <Route path="/admin/assign-tech" element={<PrivateRoute role="ADMIN"><AssignTechnicianPage/></PrivateRoute>} />
        <Route path="/admin/production-plan" element={<PrivateRoute role="ADMIN"><ProductionPlanningPage/></PrivateRoute>} />
        <Route path="/admin/insurance-claim/:reportId" element={<PrivateRoute role="ADMIN"><InsuranceClaimDetails/></PrivateRoute>} />
        <Route path="/admin/claim-summary/:claimId" element={<PrivateRoute role="ADMIN"><ClaimSummary/></PrivateRoute>} />
        <Route path="/admin/insurance/process-list" element={<PrivateRoute role="ADMIN"><InsuranceClaimList/></PrivateRoute>} />
        <Route path="/admin/vandalism/part-replacement" element={<PrivateRoute role="ADMIN"><PartsReplacement/></PrivateRoute>} />
        <Route path="/admin/report/master-report" element={<PrivateRoute role="ADMIN"><MasterReport/></PrivateRoute>} />
        <Route path="/admin/edit-purchase-order" element={<PrivateRoute role="ADMIN"><EditPurchaseOrder/></PrivateRoute>} />


        <Route path="/technician" element={<PrivateRoute role="USER"><TechnicianPanel /></PrivateRoute>} />
        <Route path="/technician/complete-profile" element={<PrivateRoute role="USER"><CompleteProfile /></PrivateRoute>} />
        <Route path="/technician/start-installation" element={<PrivateRoute role="USER"><StartInstallation /></PrivateRoute>} />
        <Route path="/technician/complete-installation" element={<PrivateRoute role="USER"><CompleteInstallationForm /></PrivateRoute>}/>
        <Route path="/technician/add-vandalism" element={<PrivateRoute role="USER"><AddVandalismReportForm /></PrivateRoute>}/>
        <Route path="/technician/start-maintenance" element={<PrivateRoute role="USER"><StartMaintenance /></PrivateRoute>} />
        <Route path="/technician/helpdesk" element={<PrivateRoute role="USER"><InstantHelpContact /></PrivateRoute>} />
        <Route path="/technician/complete-maintenance" element={<PrivateRoute role="USER"><CompleteMaintenanceForm /></PrivateRoute>} />
        <Route path="/technician/emergency-support" element={<PrivateRoute role="USER"><EmergencySupport /></PrivateRoute>} />
        <Route path="/technician/grease-refill" element={<PrivateRoute role="USER"><GreaseRefillForm /></PrivateRoute>} />
        <Route path="/technician/assign-tasks" element={<PrivateRoute role="USER"><AssignUserTaskPage /></PrivateRoute>} />
        <Route path="/technician/replaced-parts" element={<PrivateRoute role="USER"><ReplacedPartsConfirmationForm/></PrivateRoute>} />
        <Route path="/technician/machines" element={<PrivateRoute role="USER"><TaskHistory/></PrivateRoute>} />

      </Routes>
    </>
  );
}

// Only one App component, default export
export default function App() {
  return (
    <BrowserRouter>
      <AppWrapper />
    </BrowserRouter>
  );
}
