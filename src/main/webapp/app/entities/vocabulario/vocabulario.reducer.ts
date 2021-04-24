import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVocabulario, defaultValue } from 'app/shared/model/vocabulario.model';

export const ACTION_TYPES = {
  FETCH_VOCABULARIO_LIST: 'vocabulario/FETCH_VOCABULARIO_LIST',
  FETCH_VOCABULARIO: 'vocabulario/FETCH_VOCABULARIO',
  CREATE_VOCABULARIO: 'vocabulario/CREATE_VOCABULARIO',
  UPDATE_VOCABULARIO: 'vocabulario/UPDATE_VOCABULARIO',
  PARTIAL_UPDATE_VOCABULARIO: 'vocabulario/PARTIAL_UPDATE_VOCABULARIO',
  DELETE_VOCABULARIO: 'vocabulario/DELETE_VOCABULARIO',
  RESET: 'vocabulario/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVocabulario>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type VocabularioState = Readonly<typeof initialState>;

// Reducer

export default (state: VocabularioState = initialState, action): VocabularioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VOCABULARIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VOCABULARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VOCABULARIO):
    case REQUEST(ACTION_TYPES.UPDATE_VOCABULARIO):
    case REQUEST(ACTION_TYPES.DELETE_VOCABULARIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_VOCABULARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VOCABULARIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VOCABULARIO):
    case FAILURE(ACTION_TYPES.CREATE_VOCABULARIO):
    case FAILURE(ACTION_TYPES.UPDATE_VOCABULARIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_VOCABULARIO):
    case FAILURE(ACTION_TYPES.DELETE_VOCABULARIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VOCABULARIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VOCABULARIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VOCABULARIO):
    case SUCCESS(ACTION_TYPES.UPDATE_VOCABULARIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_VOCABULARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VOCABULARIO):
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

const apiUrl = 'api/vocabularios';

// Actions

export const getEntities: ICrudGetAllAction<IVocabulario> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VOCABULARIO_LIST,
  payload: axios.get<IVocabulario>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IVocabulario> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VOCABULARIO,
    payload: axios.get<IVocabulario>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVocabulario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VOCABULARIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVocabulario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VOCABULARIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IVocabulario> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_VOCABULARIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVocabulario> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VOCABULARIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
