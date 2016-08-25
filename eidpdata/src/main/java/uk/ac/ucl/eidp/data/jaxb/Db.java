package uk.ac.ucl.eidp.data.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataset" type="{}dataset-type" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dataset"
    })
@XmlRootElement(name = "db")
public class Db {

  @XmlElement(required = true)
  protected List<DatasetType> dataset;

  /**
   * Gets the value of the dataset property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the dataset property.
   *
   * <p>For example, to add a new item, do as follows:
   * <pre>
   *    getDataset().add(newItem);
   * </pre>
   *
   *
   * <p>Objects of the following type(s) are allowed in the list {@link DatasetType }
   *
   */
  public List<DatasetType> getDataset() {
    if (dataset == null) {
      dataset = new ArrayList<DatasetType>();
    }
    return this.dataset;
  }

}
