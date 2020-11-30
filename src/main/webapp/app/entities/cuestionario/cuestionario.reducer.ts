import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICuestionario, defaultValue } from 'app/shared/model/cuestionario.model';

export const ACTION_TYPES = {
  FETCH_CUESTIONARIO_LIST: 'cuestionario/FETCH_CUESTIONARIO_LIST',
  FETCH_CUESTIONARIO: 'cuestionario/FETCH_CUESTIONARIO',
  CREATE_CUESTIONARIO: 'cuestionario/CREATE_CUESTIONARIO',
  UPDATE_CUESTIONARIO: 'cuestionario/UPDATE_CUESTIONARIO',
  DELETE_CUESTIONARIO: 'cuestionario/DELETE_CUESTIONARIO',
  RESET: 'cuestionario/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICuestionario>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CuestionarioState = Readonly<typeof initialState>;

// Reducer

export default (state: CuestionarioState = initialState, action): CuestionarioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CUESTIONARIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CUESTIONARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CUESTIONARIO):
    case REQUEST(ACTION_TYPES.UPDATE_CUESTIONARIO):
    case REQUEST(ACTION_TYPES.DELETE_CUESTIONARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CUESTIONARIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CUESTIONARIO):
    case FAILURE(ACTION_TYPES.CREATE_CUESTIONARIO):
    case FAILURE(ACTION_TYPES.UPDATE_CUESTIONARIO):
    case FAILURE(ACTION_TYPES.DELETE_CUESTIONARIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUESTIONARIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUESTIONARIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CUESTIONARIO):
    case SUCCESS(ACTION_TYPES.UPDATE_CUESTIONARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CUESTIONARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cuestionarios';

// Actions

export const getEntities: ICrudGetAllAction<ICuestionario> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CUESTIONARIO_LIST,
    payload: axios.get<ICuestionario>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICuestionario> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CUESTIONARIO,
    payload: axios.get<ICuestionario>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICuestionario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CUESTIONARIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICuestionario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CUESTIONARIO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICuestionario> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CUESTIONARIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
