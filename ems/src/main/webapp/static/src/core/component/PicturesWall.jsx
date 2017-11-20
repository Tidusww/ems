import '../../css/core.css';
import React from 'react';
import {Upload, Icon, Modal} from 'antd';


class PicturesWall extends React.Component {
    propTypes:{
        actionUrl: React.PropTypes.string.isRequired,
        maxPicture: React.PropTypes.number.isRequired,
        fileList: React.PropTypes.array,
        onPictureChange: React.PropTypes.func
    };

    constructor (props) {
        super(props);

        this.state = {
            fileList: props.fileList || [],
            previewVisible: false,
            previewImageUrl: ''
        };

    }

    handleCancel = () => {
        this.setState({previewVisible: false});
    }

    handlePreview = (file) => {
        let url = file.url || file.thumbUrl;
        if(file.status == "done" && file.response){
            url = file.response.data.fileUrl;
        }
        console.log("Picture url is:"+url);
        this.setState({
            previewImage: url,
            previewVisible: true,
        });
    }

    handleChange = ({file, fileList, event}) => {
        const { status,percent } = file;
        console.log("status:"+status+" upload percent:"+percent);
        //回调
        this.props.onPictureChange({file, fileList, event});
        this.setState({fileList: fileList});
    }

    render() {
        const {previewVisible, previewImage, fileList} = this.state;
        const uploadButton = (
            <div>
                <Icon type="plus"/>
                <div className="ant-upload-text">上传</div>
            </div>
        );
        return (
            <div className="clearfix">
                <Upload
                    accept="image/*"
                    action={this.props.actionUrl}
                    listType="picture-card"
                    fileList={fileList}
                    onPreview={this.handlePreview}
                    onChange={this.handleChange}
                >
                    {fileList.length >= this.props.maxPicture ? null : uploadButton}
                </Upload>
                <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
                    <img style={{ width: '100%' }} src={previewImage}/>
                </Modal>
            </div>
        );
    }
}

exports.PicturesWall = PicturesWall;