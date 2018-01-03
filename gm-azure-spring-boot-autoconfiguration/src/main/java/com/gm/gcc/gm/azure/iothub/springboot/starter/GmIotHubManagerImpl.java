package com.gm.gcc.gm.azure.iothub.springboot.starter;

import com.gm.gcc.gm.azure.iothub.springboot.starter.exception.GmAzureIotHubSpringbootStarterException;
import com.gm.gcc.gm.azure.iothub.springboot.starter.model.CCMHAuthenticationType;
import com.gm.gcc.gm.azure.iothub.springboot.starter.model.CCMHDevice;
import com.gm.gcc.gm.azure.iothub.springboot.starter.model.CCMHRegistryStatistics;
import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.RegistryStatistics;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class GmIotHubManagerImpl implements GmIotHubManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmIotHubManagerImpl.class);

    private RegistryManager registryManager;


    public GmIotHubManagerImpl(RegistryManager registryManager) {

        this.registryManager = registryManager;
    }


    @Override
    public CCMHDevice registerDevice(CCMHDevice ccmhDevice) {

        try {
            Device device = Device.createDevice(ccmhDevice.getDeviceId(), AuthenticationType.SELF_SIGNED);
            String thumbPrint = getThumbPrint(ccmhDevice.getCert());
            device.setThumbprint(thumbPrint, thumbPrint);

            device = this.registryManager.addDevice(device);
//            ccmhDevice.setDeviceId(moduleId);
            ccmhDevice.setCcmhAuthenticationType(CCMHAuthenticationType.SELF_SIGNED);
            ccmhDevice.setRegistryHubHost(this.registryManager.getDeviceConnectionString(device));
//            ccmhDevice.setVin(vin);
            return ccmhDevice;
        } catch (CertificateException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        } catch (IotHubException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }


    @Override
    public CCMHRegistryStatistics getRegistryStatistics() {

        try {
            RegistryStatistics registryStatistics = this.registryManager.getStatistics();
            CCMHRegistryStatistics ccmhRegistryStatistics = new CCMHRegistryStatistics();
            ccmhRegistryStatistics.setDisabledDeviceCount(registryStatistics.getDisabledDeviceCount());
            ccmhRegistryStatistics.setEnabledDeviceCount(registryStatistics.getEnabledDeviceCount());
            ccmhRegistryStatistics.setTotalDeviceCount(registryStatistics.getTotalDeviceCount());
            return ccmhRegistryStatistics;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        } catch (IotHubException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("", e);
            throw new GmAzureIotHubSpringbootStarterException(e);
        }
    }


    private String getThumbPrint(String certificate) throws CertificateException, NoSuchAlgorithmException {

        X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
          .generateCertificate(new ByteArrayInputStream(certificate.getBytes()));
        return getThumbPrint(x509Certificate);
    }


    private String getThumbPrint(X509Certificate cert) throws NoSuchAlgorithmException, CertificateEncodingException {

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] der = cert.getEncoded();
        md.update(der);
        byte[] digest = md.digest();
        return hexify(digest);

    }


    private String hexify(byte bytes[]) {

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            buf.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
            buf.append(hexDigits[bytes[i] & 0x0f]);
        }

        return buf.toString();
    }
}
