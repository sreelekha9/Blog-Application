var commentArray=[];
	var saveComment = function(data) {

		// Convert pings to human readable format
		/*$(Object.keys(data.pings)).each(function(index, userId) {
		    var fullname = data.pings[userId];
		    var pingText = '@' + fullname;
		    data.content = data.content.replace(new RegExp('@' + userId, 'g'), pingText);
		});*/
		

		return data;
	}
	function addQuestion()
	{
		var msg=prompt("Enter a Question?");
		if (msg != null) {
			
						$.ajax({
			                type: 'POST',
			                url: '/question',
			                data: '{"content":"'+msg+'"}',
			                contentType: "application/json",
			                dataType: 'json'
			            })
			            .done(function(data) {
			            	location.reload();
			                return resolve(data);
			            })
			            .fail(function(error) {
			                console.error(error);
			                return reject(error);
			            })
			            .always(function() {
			                // called after done or fail
			            });
								 
		}
	}
	var currentSelectedQuestion=null;
	var createCommentContainer=function(container,questionid)
	{
		$(container).comments({
			fieldMappings: {
			    id: 'id',
			    parent: 'parent',
			    created: 'creationDate',
			    modified: 'modificationDate',
			    creator: 'createdBy',
			    fullname: 'createdBy'
			},
			profilePictureURL: 'https://viima-app.s3.amazonaws.com/media/public/defaults/user-icon.png',
			currentUserId: 1,
			roundProfilePictures: true,
			textareaRows: 1,
			currentQuestionId:questionid,
			enableAttachments: false,
			enableHashtags: false,
			enablePinging: false,
			searchUsers: function(term, success, error) {
			    setTimeout(function() {
			        success(usersArray.filter(function(user) {
			            var containsSearchTerm = user.fullname.toLowerCase().indexOf(term.toLowerCase()) != -1;
			            var isNotSelf = user.id != 1;
			            return containsSearchTerm && isNotSelf;
			        }));
			    }, 500);
			},
			getComments: function(success, error) {
				var questionid=currentSelectedQuestion.attr('data-questionid');
				$.get( "/question/comments/"+questionid, function( data ) {
					
					success(data);
					});
			},
			postComment: function(data, success, error) {
				var questionid=currentSelectedQuestion.attr('data-questionid');
				var postdata={
						content:data.content,
						question:{
							id:parseInt(questionid)
						}
						
				};
				if(data.parent)
					{
						postdata["parentObj"]={
							id:data.parent
						};
						
					}
				
				$.ajax({
	                type: 'POST',
	                url: '/question/comments',
	                data: JSON.stringify(postdata),
	                contentType: "application/json",
	                dataType: 'json'
	            })
	            .done(function(data) {
	            	data["parent"]=data.parentObj.id;
	            	success(saveComment(data));
	                return resolve(data);
	            })
	            .fail(function(error) {
	                console.error(error);
	                return reject(error);
	            })
	            .always(function() {
	                // called after done or fail
	            });
				/*setTimeout(function() {
					success(saveComment(data));
				}, 500);*/
			},
			putComment: function(data, success, error) {
				setTimeout(function() {
					success(saveComment(data));
				}, 500);
			},
			deleteComment: function(data, success, error) {
				setTimeout(function() {
					success();
				}, 500);
			},
			upvoteComment: function(data, success, error) {
				setTimeout(function() {
					success(data);
				}, 500);
			},
			uploadAttachments: function(dataArray, success, error) {
				setTimeout(function() {
					success(dataArray);
				}, 500);
			},
		});
	}

$( document ).ready(function() {
	

	$(".question-content a").click(function(){
		
		var question_container=$(this).parent().parent();
		
		var questionid=question_container.attr('data-questionid');
		question_container.find(".comments-container").toggle();
		if(question_container.find(".comments-container").is(':visible'))
		{
			$(this).text('hide');
			
			if(currentSelectedQuestion!=null && currentSelectedQuestion.attr('data-questionid')!=questionid)
			{
				currentSelectedQuestion.find(".comments-container").hide();
			}
			currentSelectedQuestion=question_container;
			createCommentContainer(question_container.find(".comments-container")[0],questionid);
			
		}else{
			$(this).text('Reply');
		}
		
	});
	
});