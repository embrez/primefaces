/**
 * The MIT License
 *
 * Copyright (c) 2009-2019 PrimeTek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primefaces.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.input.BoundedInputStream;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.util.FileUploadUtils;

/**
 *
 * UploadedFile implementation based on Commons FileUpload FileItem
 */
public class DefaultUploadedFile implements UploadedFile, Serializable {

    private static final long serialVersionUID = 1L;

    private FileItem fileItem;
    private Long sizeLimit;

    public DefaultUploadedFile() {
    }

    public DefaultUploadedFile(FileItem fileItem, FileUpload fileUpload) {
        this.fileItem = fileItem;
        this.sizeLimit = fileUpload.getSizeLimit();
    }

    @Override
    public String getFileName() {
        return FileUploadUtils.getValidFilename(fileItem.getName());
    }

    @Override
    public List<String> getFileNames() {
        return null;
    }

    @Override
    public InputStream getInputstream() throws IOException {
        return sizeLimit == null ? fileItem.getInputStream() : new BoundedInputStream(fileItem.getInputStream(), sizeLimit);
    }

    @Override
    public long getSize() {
        return fileItem.getSize();
    }

    @Override
    public byte[] getContents() {
        return fileItem.get();
    }

    @Override
    public String getContentType() {
        return fileItem.getContentType();
    }

    @Override
    public void write(String filePath) throws Exception {
        String validFilePath = FileUploadUtils.getValidFilePath(filePath);
        fileItem.write(new File(validFilePath));
    }

}
