/*清除所有的弹窗*/
function clearmodal(){
	var modalname = "modal";
	var em=document.getElementsByClassName(modalname);
	for(var i=em.length-1;i>=0;i--){var p=em[i];p.parentNode.removeChild(p);}
}
/*构建显示弹窗元素*/
function showPhoto(src){
	var modalname = "modal"
	var imagename = "modal-content"
	var closename = "close"
	var modal = document.createElement('div');
	modal.setAttribute('class',modalname);
	
	var modalImg = document.createElement('img');
	modalImg.setAttribute('class',imagename);
	modal.appendChild(modalImg);
	modalImg.setAttribute('src',src);
	
	var modalclose = document.createElement('span');
	modalclose.setAttribute('class',closename);
	modalclose.innerHTML = "&times;";
	modalclose.onclick = clearmodal;
	modal.appendChild(modalclose);
	
	/*调整图片位置*/
	var imgwh = modalImg.width;
	var imght = modalImg.height;
	var htmlWidth = window.innerWidth;            
	var htmlHeight = window.innerHeight;
	var scale = imght*htmlWidth/(imgwh*htmlHeight);
	if(scale<1){
		/*横着长*/
		modalImg.width = htmlWidth;
		var tmp = htmlHeight*(1-scale)/2;
		modalImg.setAttribute('style',"padding-top:"+tmp+"px");
	}
	else{
		modalImg.height = htmlHeight;
	}
		
	document.body.appendChild(modal);
	modal.setAttribute("style","display:block");
}
function showPhotoElement(ImgElement){
	var modalname = "modal"
	var imagename = "modal-content"
	var closename = "close"
	var modal = document.createElement('div');
	modal.setAttribute('class',modalname);
	
	var modalImg = document.createElement('img');
	modalImg.setAttribute('class',imagename);
	modal.appendChild(modalImg);
	modalImg.src = ImgElement.src;
	modalImg.onclick = clearmodal;
	
	var modalclose = document.createElement('span');
	modalclose.setAttribute('class',closename);
	modalclose.innerHTML = "&times;";
	modalclose.onclick = clearmodal;
	modal.appendChild(modalclose);
	
	/*调整图片位置*/
	var imgwh = modalImg.width;
	var imght = modalImg.height;
	var htmlWidth = window.innerWidth;            
	var htmlHeight = window.innerHeight;
	var scale = imght*htmlWidth/(imgwh*htmlHeight);
	if(scale<1){
		/*横着长*/
		modalImg.width = htmlWidth;
		var tmp = htmlHeight*(1-scale)/2;
		modalImg.setAttribute('style',"padding-top:"+tmp+"px");
	}
	else{
		modalImg.height = htmlHeight;
	}
		
	document.body.appendChild(modal);
	modal.setAttribute("style","display:block");
}