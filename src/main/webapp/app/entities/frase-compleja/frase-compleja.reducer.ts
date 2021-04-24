import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFraseCompleja, defaultValue } from 'app/shared/model/frase-compleja.model';

export const ACTION_TYPES = {
  FETCH_FRASECOMPLEJA_LIST: 'fraseCompleja/FETCH_FRASECOMPLEJA_LIST',
  FETCH_FRASECOMPLEJA: 'fraseCompleja/FETCH_FRASECOMPLEJA',
  CREATE_FRASECOMPLEJA: 'fraseCompleja/CREATE_FRASECOMPLEJA',
  UPDATE_FRASECOMPLEJA: 'fraseCompleja/UPDATE_FRASECOMPLEJA',
  PARTIAL_UPDATE_FRASECOMPLEJA: 'fraseCompleja/PARTIAL_UPDATE_FRASECOMPLEJA',
  DELETE_FRASECOMPLEJA: 'fraseCompleja/DELETE_FRASECOMPLEJA',
  RESET: 'fraseCompleja/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFraseCompleja>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type FraseComplejaState = Readonly<typeof initialState>;

// Reducer

export default (state: FraseComplejaState = initialState, action): FraseComplejaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FRASECOMPLEJA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FRASECOMPLEJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_FRASECOMPLEJA):
    case REQUEST(ACTION_TYPES.UPDATE_FRASECOMPLEJA):
    case REQUEST(ACTION_TYPES.DELETE_FRASECOMPLEJA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_FRASECOMPLEJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_FRASECOMPLEJA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FRASECOMPLEJA):
    case FAILURE(ACTION_TYPES.CREATE_FRASECOMPLEJA):
    case FAILURE(ACTION_TYPES.UPDATE_FRASECOMPLEJA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_FRASECOMPLEJA):
    case FAILURE(ACTION_TYPES.DELETE_FRASECOMPLEJA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FRASECOMPLEJA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_FRASECOMPLEJA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_FRASECOMPLEJA):
    case SUCCESS(ACTION_TYPES.UPDATE_FRASECOMPLEJA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_FRASECOMPLEJA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_FRASECOMPLEJA):
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

const apiUrl = 'api/frase-complejas';

// Actions

export const getEntities: ICrudGetAllAction<IFraseCompleja> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FRASECOMPLEJA_LIST,
  payload: axios.get<IFraseCompleja>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IFraseCompleja> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FRASECOMPLEJA,
    payload: axios.get<IFraseCompleja>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IFraseCompleja> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FRASECOMPLEJA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFraseCompleja> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FRASECOMPLEJA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IFraseCompleja> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_FRASECOMPLEJA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFraseCompleja> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FRASECOMPLEJA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
