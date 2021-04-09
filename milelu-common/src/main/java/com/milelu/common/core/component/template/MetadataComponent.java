package com.milelu.common.core.component.template;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.Metadata;
import com.milelu.common.utils.CommonUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author MILELU
 * @date 2020/12/23 10:57
 */
@Component
public class MetadataComponent {
    /**
     *文件metadata
     */
    public static final String METADATA_FILE = "metadata.data";
    /**
     *文件data
     */
    public static final String DATA_FILE = "data.data";

    /**
     * 更新模板元数据
     *
     * @param filePath
     * @param metadata
     * @return whether the update is successful
     */
    public boolean updateTemplateMetadata(String filePath, Metadata metadata) {
        File file = new File(filePath);
        String dirPath = file.getParent();
        Map<String, Metadata> metadataMap = getTemplateMetadataMap(dirPath);
        metadataMap.put(file.getName(), metadata);
        try {
            saveTemplateMetadata(dirPath, metadataMap);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Map<String, Metadata> getTemplateMetadataMap(String dirPath) {
        File file = new File(dirPath + Constants.SEPARATOR + METADATA_FILE);
        Map<String, Metadata> metadataMap;
        if (CommonUtils.isNotEmptyObject(file)) {
            try {
                //TODO:
                metadataMap = Constants.objectMapper.readValue(file, Constants.objectMapper.getTypeFactory()
                        .constructMapLikeType(CaseInsensitiveMap.class, String.class, Metadata.class));
            } catch (IOException | ClassCastException e) {
                e.printStackTrace();
                metadataMap = new CaseInsensitiveMap<>();
            }
        }else {
            metadataMap = new CaseInsensitiveMap<>();
        }
        return metadataMap;
    }

    /**
     * 保存模板元数据
     *
     * @param dirPath
     * @param metadataMap
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void saveTemplateMetadata(String dirPath, Map<String, Metadata> metadataMap)
            throws JsonGenerationException, JsonMappingException, IOException {
        File file = new File(dirPath + Constants.SEPARATOR + METADATA_FILE);
        if (CommonUtils.isNotEmptyObject(file)) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            Constants.objectMapper.writeValue(file, metadataMap);
        }

    }

    public Metadata getTemplateMetadata(String filePath) {
        File file = new File(filePath);
        Metadata metadata = getTemplateMetadataMap(file.getParent()).get(file.getName());
        if (null == metadata) {
            metadata = new Metadata();
            metadata.setUseDynamic(true);
        }
        return metadata;
    }

    public boolean deleteTemplateMetadata(String filePath) {
        File file = new File(filePath);
        String dirPath = file.getParent();
        Map<String, Metadata> metadataMap = getTemplateMetadataMap(dirPath);
        metadataMap.remove(file.getName());
        try {
            saveTemplateMetadata(dirPath, metadataMap);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
