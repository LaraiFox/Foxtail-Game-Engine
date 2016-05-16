uniform mat4 FOXTAIL_MATRIX_MODEL;		// Current object's model matrix
uniform mat4 FOXTAIL_MATRIX_VIEW;		// Current camera's view matrix
uniform mat4 FOXTAIL_MATRIX_PROJECTION;	// Current camera's projection matrix

uniform mat4 FOXTAIL_MATRIX_MVP;		// Current object's and camera's combined model, view and projection matrices
uniform mat4 FOXTAIL_MATRIX_I_M;		// Inverse of current object's model matrix
uniform mat4 FOXTAIL_MATRIX_MV;			// Current object's and camera's combined model and view matrices
uniform mat4 FOXTAIL_MATRIX_T_MV;		// Inverse of current object's and camera's combined model and view matrices
uniform mat4 FOXTAIL_MATRIX_IT_MV;		// Inverse transpose of current object's and camera's combined model and view matrices
uniform mat4 FOXTAIL_MATRIX_VP;			// Current camera's combined view and projection matrices

	// FOXTAIL_MATRIX_TEXTURE IS CURRENTLY UNUSED HOWEVER //
	//  IT MAY BE USED FOR TEXTURE UV MANIPULATION LATER  //
uniform mat4 FOXTAIL_MATRIX_TEXTURE;