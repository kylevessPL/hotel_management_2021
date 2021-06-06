import {CustomInput, Input, Label} from "reactstrap";
import React from "react";

const BrowseImageInput =
    ({
         field: { name },
         filename,
         setFilename,
         accept,
         className,
         form: { isSubmitting, setFieldValue },
    }) => {

    const fileInput = React.createRef();

    const handleChange = e => {
        e.preventDefault();
        let file = e.target.files[0];
        if (file) {
            setFilename(file.name);
            setFieldValue(name, file);
        }
    }

    const handleClick = e => {
        e.preventDefault();
        fileInput.current.click();
    }

    return (
        <>
            <Label for="browseAvatar">Upload avatar</Label>
            <CustomInput
                type="file"
                name="browseAvatar"
                label={filename || 'Choose an image file'}
                disabled={isSubmitting}
                className={className}
                onClick={handleClick} />
            <Input
                type="file"
                name={name}
                accept={accept}
                className="d-none"
                innerRef={fileInput}
                onChange={handleChange} />
        </>
    );
}

export default BrowseImageInput;