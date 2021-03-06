package datawave.ingest.data;

import datawave.data.hash.UID;

import org.apache.accumulo.core.security.ColumnVisibility;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Generic container used to hold raw source data. It is used in various parts of the ingest framework and is typically persisted as an "event" within DW's
 * shard table schema
 */
public interface RawRecordContainer {
    
    Map<String,String> getSecurityMarkings();
    
    void setSecurityMarkings(Map<String,String> securityMarkings);
    
    void addSecurityMarking(String domain, String marking);
    
    boolean hasSecurityMarking(String domain, String marking);
    
    UID getId();
    
    void setId(UID id);
    
    Type getDataType();
    
    void setDataType(Type dataType);
    
    /**
     * Gets the primary date associated with the record, a.k.a the "event date"
     * 
     * @return the date for this raw record
     */
    long getDate();
    
    /**
     * In the DW data model, this date is often referred to as "event date" and represents the primary date value for the record. At ingest time, it is
     * typically encoded as part of the Accumulo row id (in YYYYMMDD format) for the record's associated shard table entries. Clients may attach any semantics
     * they wish to this date, but should be aware that it directly impacts the sort order of their data in Accumulo.
     *
     * <p>
     * Thus, this date is typically leveraged by DW's query api as the basis for 'begin' / 'end' date ranges for user queries. However, DW also has the ability
     * to leverage other dates within your records at query time, if needed. So, for date filtering concerns, you're not necessarily stuck with your choice of
     * 'event' date in this regard
     *
     * @param date
     *            primary date to be associated with the record, a.k.a. the "event date"
     */
    void setDate(long date);
    
    Collection<String> getErrors();
    
    void setErrors(Collection<String> errors);
    
    void addError(String error);
    
    void removeError(String error);
    
    boolean hasError(String error);
    
    boolean fatalError();
    
    boolean ignorableError();
    
    void clearErrors();
    
    Collection<String> getAltIds();
    
    void setAltIds(Collection<String> altIds);
    
    void addAltId(String altId);
    
    boolean hasAltId(String altId);
    
    String getRawFileName();
    
    void setRawFileName(String rawFileName);
    
    long getRawRecordNumber();
    
    void setRawRecordNumber(long rawRecordNumber);
    
    long getRawFileTimestamp();
    
    void setRawFileTimestamp(long rawRecordTimestamp);
    
    byte[] getRawData();
    
    void setRawData(byte[] rawData);
    
    Object getAuxData();
    
    void setAuxData(Object auxData);
    
    void setRawDataAndGenerateId(byte[] rawData);
    
    void setRawDataAndGenerateId(byte[] rawData, String extra);
    
    RawRecordContainer copy();
    
    long getDataOutputSize();
    
    void write(DataOutput dataOutput) throws IOException;
    
    ColumnVisibility getVisibility();
    
    void setVisibility(ColumnVisibility visibility);
    
    /**
     * Return a date to use in the UID for this RawRecord
     * 
     * @return null or date object
     */
    Date getTimeForUID();
    
    /**
     * Does this record need to be masked.
     * 
     * @return true or false
     */
    boolean isRequiresMasking();
    
    /**
     * Clear/reset the current state to support object reuse
     */
    void clear();
}
